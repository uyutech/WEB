package com.zhuanquan.app.server.task;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.zhuanquan.app.common.component.cache.RedisKeyBuilder;
import com.zhuanquan.app.common.component.cache.redis.lock.RedisSimpleLock;
import com.zhuanquan.app.common.exception.BizException;
import com.zhuanquan.app.common.model.common.RegisterAppointment;
import com.zhuanquan.app.dal.dao.common.RegisterAppointmentDAO;
import com.zhuanquan.app.server.openapi.OpenApiService;
import com.zhuanquan.app.server.service.TransactionService;

@Component
public class RegisterAppointmentSyncDataTask {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private RedisSimpleLock redisSimpleLock;

	@Resource
	private RegisterAppointmentDAO registerAppointmentDAO;

	@Resource
	private TransactionService transactionService;

	private static final int CONCURRENT_MAX_THREAD = 4;

	/**
	 * 单个批次最大执行数量
	 */
	private static final int BATCH_LIMIT = 100;

	private AtomicInteger counter = new AtomicInteger(0);

	/**
	 * 执行的线程池
	 */
	private ExecutorService executorService = Executors.newFixedThreadPool(CONCURRENT_MAX_THREAD);

	@Scheduled(cron = "0 0/5 * * * ?")
	public void run() {

		String key = RedisKeyBuilder.getRegisterAppointmentTaskLock();
		boolean locked = redisSimpleLock.tryLock(key, 10, TimeUnit.MINUTES);

		if (!locked) {
			return;
		}

		try {

			logger.info("###########################################################");
			logger.info("RegisterAppointmentSyncDataTask begin to execute!!!!!!!");

			executeJob();

			logger.info("###########################################################");
			logger.info("RegisterAppointmentSyncDataTask end to execute!!!!!!!");

		} catch (BizException e) {

			logger.error("RegisterAppointmentSyncDataTask execute witn biz exception", e);

			throw e;
		} catch (Exception e) {

			logger.error("RegisterAppointmentSyncDataTask execute witn unexpected exception", e);

		} finally {

			try {
				// 释放锁
				redisSimpleLock.releaseLock(key);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 执行job
	 */
	private void executeJob() {

		long fromIndex = 0;

		while (true) {

			List<RegisterAppointment> list = registerAppointmentDAO.queryUnSyncedRecord(fromIndex, BATCH_LIMIT);

			// 查询为空即退出
			if (CollectionUtils.isEmpty(list)) {
				break;
			}

			//
			for (RegisterAppointment record : list) {
				// 提交

				executorService.submit(new RegAppointSyncDataRunnable(transactionService, record));

				counter.incrementAndGet();
			}

			// 取最后一条记录的id
			fromIndex = list.get(list.size() - 1).getId();

			// 休息一会
			try {
				Thread.sleep(10 * 1000);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		
		executorService.shutdown();

		while (true) {

			if (executorService.isTerminated()) {

				break;
			}

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();

			}
		}

	}

	/**
	 * 
	 * @author zhangjun
	 *
	 */
	public class RegAppointSyncDataRunnable implements Runnable {

		private RegisterAppointment appointment;

		private TransactionService transactionService;

		public RegisterAppointment getAppointment() {
			return appointment;
		}

		public void setAppointment(RegisterAppointment appointment) {
			this.appointment = appointment;
		}

		public RegAppointSyncDataRunnable(TransactionService service, RegisterAppointment record) {

			appointment = record;

			transactionService = service;
		}

		@Override
		public void run() {

			logger.info("预约用户数据同步开始:[openId]:" + appointment.getOpenId() + ",[channelType]:"
					+ appointment.getChannelType());

			try {
				transactionService.syncRegisterAppointmentUserData(appointment);
			} catch (Exception e) {

;				logger.error("RegAppointSyncDataRunnable run with exception,[record]:" + JSON.toJSONString(appointment),
						e);

			} finally {
				counter.getAndDecrement();
			}

			logger.info("预约用户数据同步完成:[openId]:" + appointment.getOpenId() + ",[channelType]:"
					+ appointment.getChannelType());
		}

	}

}