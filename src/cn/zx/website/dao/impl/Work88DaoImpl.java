package cn.zx.website.dao.impl;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import cn.zx.website.dao.Work88Dao;
import cn.zx.website.db.QueryHelper;
import cn.zx.website.domain.Work88;
import cn.zx.website.util.Constants;

public class Work88DaoImpl implements Work88Dao {
	public static final String INDEX_PATH = "/home/pi/index/work88";
	public static final String FIELD_ID = "id";
	public static final String FIELD_DATE = "date";
	public static final String FIELD_TITLE = "title";
	public static final String FIELD_HREF = "href";
	public static final String FIELD_COLLECTED = "collected";
	public static final String FIELD_DELETED = "deleted";
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
		return QueryHelper.query_slice(Work88.class, sql, page, Constants.WORK_THREAD_PAGE_COUNT, (Object[]) null);
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

	@Override
	public List<Work88> searchWork88(String keyword) {
		List<Work88> list = new ArrayList<>();
		// 实例化IKAnalyzer分词器
		Analyzer analyzer = new IKAnalyzer();

		Directory directory = null;
		IndexReader ireader = null;
		IndexSearcher isearcher = null;
		try {
			// 建立索引对象
			directory = FSDirectory.open(new File(INDEX_PATH));
			// 实例化搜索器
			ireader = IndexReader.open(directory);
			isearcher = new IndexSearcher(ireader);

			// 使用QueryParser查询分析器构造Query对象
			QueryParser qp = new QueryParser(Version.LUCENE_36, FIELD_TITLE, analyzer);
			qp.setDefaultOperator(QueryParser.AND_OPERATOR);
			Query query = qp.parse(keyword);

			// 搜索相似度最高的5条记录
			TopDocs topDocs = isearcher.search(query, 1000);
			System.out.println("命中：" + topDocs.totalHits);
			// 输出结果
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			for (int i = 0; i < topDocs.totalHits; i++) {
				Document targetDoc = isearcher.doc(scoreDocs[i].doc);
				Work88 work = new Work88(Integer.valueOf(targetDoc.get(FIELD_ID)), targetDoc.get(FIELD_TITLE), targetDoc.get(FIELD_HREF), Timestamp.valueOf(targetDoc.get(FIELD_DATE)),
						Boolean.valueOf(targetDoc.get(FIELD_COLLECTED)), Boolean.valueOf(targetDoc.get(FIELD_DELETED)));
				list.add(work);
			}
			isearcher.close();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			if (ireader != null) {
				try {
					ireader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (directory != null) {
				try {
					directory.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	@Override
	public void createWorkThread(Work88 thread) {
		String sql = "INSERT INTO zju88_work_thread(title,href,createDate) VALUES(?,?,?)";
		QueryHelper.update(sql, thread.getTitle(), thread.getHref(), thread.getCreateDate());
	}
}