package cn.zx.website.timertask;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.TimerTask;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import cn.zx.website.db.DaoFactory;
import cn.zx.website.domain.Blog;
import cn.zx.website.util.Constants;

public class BlogIndexTimerTask extends TimerTask {
	private final static Log log = LogFactory.getLog(BlogIndexTimerTask.class);

	@Override
	public void run() {
		try {
			FileUtils
					.cleanDirectory(new File(Constants.INDEX_DIRECTORY_OF_BLOG));
			log.info("Index for blog cleaned!");

			Analyzer analyzer = new SmartChineseAnalyzer(Version.LUCENE_CURRENT);

			Directory directory = FSDirectory.open(new File(
					Constants.INDEX_DIRECTORY_OF_BLOG));
			IndexWriterConfig config = new IndexWriterConfig(
					Version.LUCENE_CURRENT, analyzer);
			IndexWriter iwriter = new IndexWriter(directory, config);

			List<Blog> blogs = DaoFactory.getBlogDao().findAllBlogs();
			for (Blog blog : blogs) {
				Document doc = new Document();
				doc.add(new Field("createDate", Constants.DATE_FORMAT
						.format(blog.getCreateDate()), TextField.TYPE_STORED));
				doc.add(new Field("content", blog.getContent(),
						TextField.TYPE_STORED));
				iwriter.addDocument(doc);
			}
			iwriter.close();
			directory.close();
			log.info("Index for blog generated!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}