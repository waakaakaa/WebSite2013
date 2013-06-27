package cn.zx.website.dao;

import java.util.List;

import cn.zx.website.domain.ZJU88Thread;

public interface ZJU88ThreadDao {

	void createWorkThread(ZJU88Thread thread);

	List<ZJU88Thread> getAllWorkThreads();

	List<ZJU88Thread> getWorkThreads(int page);

	void updateCollected(int id);

	void updateUncollected(int id);

	void updateDeleted(int id);

	void updateUndeleted(int id);

	ZJU88Thread readById(int id);

}