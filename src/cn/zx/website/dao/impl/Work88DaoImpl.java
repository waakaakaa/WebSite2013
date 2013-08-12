package cn.zx.website.dao.impl;

import java.util.List;

import cn.zx.website.dao.Work88Dao;
import cn.zx.website.db.QueryHelper;
import cn.zx.website.domain.Work88;
import cn.zx.website.util.Constants;

public class Work88DaoImpl implements Work88Dao {
	@Override
	public List<Work88> getAllWorkThreads() {
		String sql = "SELECT * FROM zju88_work_thread WHERE deleted=0 ORDER BY createDate DESC";
		return QueryHelper.query(Work88.class, sql, (Object[]) null);
	}

	@Override
	public List<Work88> getAllCollectedWorkThreads() {
		String sql = "SELECT * FROM zju88_work_thread WHERE collected=1 ORDER BY createDate DESC";
		return QueryHelper.query(Work88.class, sql, (Object[]) null);
	}

	@Override
	public List<Work88> getAllDeletedWorkThreads() {
		String sql = "SELECT * FROM zju88_work_thread WHERE deleted=1 ORDER BY createDate DESC";
		return QueryHelper.query(Work88.class, sql, (Object[]) null);
	}

	@Override
	public List<Work88> getWorkThreads(int page) {
		String sql = "SELECT * FROM zju88_work_thread ORDER BY createDate DESC";
		return QueryHelper.query_slice(Work88.class, sql, page,
				Constants.WORK_THREAD_PAGE_COUNT, (Object[]) null);
	}

	@Override
	public Work88 readById(int id) {
		String sql = "SELECT * FROM zju88_work_thread WHERE id=?";
		return QueryHelper.read(Work88.class, sql, id);
	}

	@Override
	public void updateCollected(int id) {
		String sql = "UPDATE zju88_work_thread SET collected=1 WHERE id=?";
		QueryHelper.update(sql, id);
	}

	@Override
	public void updateUncollected(int id) {
		String sql = "UPDATE zju88_work_thread SET collected=0 WHERE id=?";
		QueryHelper.update(sql, id);
	}

	@Override
	public void updateDeleted(int id) {
		String sql = "UPDATE zju88_work_thread SET deleted=1 WHERE id=?";
		QueryHelper.update(sql, id);
	}

	@Override
	public void updateUndeleted(int id) {
		String sql = "UPDATE zju88_work_thread SET deleted=0 WHERE id=?";
		QueryHelper.update(sql, id);
	}
}