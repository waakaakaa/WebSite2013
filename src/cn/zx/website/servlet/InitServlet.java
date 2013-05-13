package cn.zx.website.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import cn.zx.website.db.DBManager;
import cn.zx.website.db.DaoFactory;
import cn.zx.website.domain.Blog;
import cn.zx.website.util.Constants;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/init", loadOnStartup = 3)
public class InitServlet extends HttpServlet {
	private final static Log log = LogFactory.getLog(InitServlet.class);

	@Override
	public void init() throws ServletException {
		initDatabase();
		generateIndexForBlog();
	}

	private void initDatabase() {
		try {
			DBManager.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void generateIndexForBlog() {
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					FileUtils.cleanDirectory(new File(Constants.INDEX_DIRECTORY_OF_BLOG));
					log.info("Index for blog cleaned!");

					Analyzer analyzer = new SmartChineseAnalyzer(Version.LUCENE_CURRENT);

					Directory directory = FSDirectory.open(new File(Constants.INDEX_DIRECTORY_OF_BLOG));
					IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_CURRENT, analyzer);
					IndexWriter iwriter = new IndexWriter(directory, config);

					List<Blog> blogs = DaoFactory.getBlogDao().findAllBlogs();
					for (Blog blog : blogs) {
						Document doc = new Document();
						doc.add(new Field("createDate", Constants.DATE_FORMAT.format(blog.getCreateDate()), TextField.TYPE_STORED));
						doc.add(new Field("content", blog.getContent(), TextField.TYPE_STORED));
						iwriter.addDocument(doc);
					}
					iwriter.close();
					directory.close();
					log.info("Index for blog generated!");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}, 0, 1000 * 60 * 15);
	}
}