package cn.zx.website.dao.impl;

import java.util.List;

import cn.zx.website.dao.ZJU88ThreadDao;
import cn.zx.website.db.QueryHelper;
import cn.zx.website.domain.ZJU88Thread;
import cn.zx.website.util.Constants;

public class ZJU88ThreadDaoImpl implements ZJU88ThreadDao {
	@Override
	public List<ZJU88Thread> getAllWorkThreads() {
		String sql = "SELECT * FROM zju88_work_thread WHERE deleted=0 ORDER BY createDate DESC";
		return QueryHelper.query(ZJU88Thread.class, sql, null);
	}

	@Override
	public List<ZJU88Thread> getAllCollectedWorkThreads() {
		String sql = "SELECT * FROM zju88_work_thread WHERE collected=1 ORDER BY createDate DESC";
		return QueryHelper.query(ZJU88Thread.class, sql, null);
	}

	@Override
	public List<ZJU88Thread> getAllDeletedWorkThreads() {
		String sql = "SELECT * FROM zju88_work_thread WHERE deleted=1 ORDER BY createDate DESC";
		return QueryHelper.query(ZJU88Thread.class, sql, null);
	}

	@Override
	public List<ZJU88Thread> getWorkThreads(int page) {
		String sql = "SELECT * FROM zju88_work_thread ORDER BY createDate DESC";
		return QueryHelper.query_slice(ZJU88Thread.class, sql, page,
				Constants.WORK_THREAD_PAGE_COUNT, null);
	}

	@Override
	public ZJU88Thread readById(int id) {
		String sql = "SELECT * FROM zju88_work_thread WHERE id=?";
		return QueryHelper.read(ZJU88Thread.class, sql, id);
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