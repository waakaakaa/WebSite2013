package cn.zx.website.dao;

import java.util.List;

import cn.zx.website.domain.ZJU88Thread;

public interface ZJU88ThreadDao {

	void createWorkThread(ZJU88Thread thread);

	List<ZJU88Thread> getWorkThreads();

}