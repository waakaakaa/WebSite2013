package cn.zx.website.dao.impl;

import java.util.List;

import cn.zx.website.dao.ZJU88ThreadDao;
import cn.zx.website.db.QueryHelper;
import cn.zx.website.domain.Blog;
import cn.zx.website.domain.ZJU88Thread;
import cn.zx.website.util.Constants;

public class ZJU88ThreadDaoImpl implements ZJU88ThreadDao {
	@Override
	public void createWorkThread(ZJU88Thread thread) {
		String sql = "INSERT INTO zju88_work_thread(title,href,createDate) VALUES(?,?,?)";
		QueryHelper.update(sql, thread.getTitle(), thread.getHref(),
				thread.getCreateDate());
	}

	@Override
	public List<ZJU88Thread> getAllWorkThreads() {
		String sql = "SELECT * FROM zju88_work_thread ORDER BY createDate DESC";
		return QueryHelper.query(ZJU88Thread.class, sql, null);
	}

	@Override
	public List<ZJU88Thread> getWorkThreads(int page) {
		String sql = "SELECT * FROM zju88_work_thread ORDER BY createDate DESC";
		return QueryHelper.query_slice(ZJU88Thread.class, sql, page,
				Constants.WORK_THREAD_PAGE_COUNT, null);
	}
}