package cn.zx.website.dao;

import java.util.List;

import cn.zx.website.domain.Work88;

public interface Work88Dao {

	List<Work88> getAllWorkThreads();

	List<Work88> getWorkThreads(int page);

	void updateCollected(int id);

	void updateUncollected(int id);

	void updateDeleted(int id);

	void updateUndeleted(int id);

	Work88 readById(int id);

	List<Work88> getAllDeletedWorkThreads();

	List<Work88> getAllCollectedWorkThreads();

	List<Work88> searchWork88(String keyword);

	void createWorkThread(Work88 thread);

}