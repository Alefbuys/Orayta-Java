package book;

import java.util.List;
import java.util.Map;

import book.contents.BookContents;
import book.contents.IBookMetaData;

import search.IPuretextSearchable;
import search.chapterMapping.ISortedNumberList;
import tree.IHasID;
import tree.IHasPath;

/*
 * THE Book class.
 * From this class you can get a chpter's text (given it's address),
 *  get a tree of the chapters,
 *  and much more.
 */

public class Book implements Comparable<Book>, IHasID, IPuretextSearchable, IHasPath
{
	protected BookContents mContents = null;
	
	//TODO: ???
	protected IBookMetaData metaData;
	
	protected int mBookUID;
	protected String mDisplayName;
	protected String mDisplayNameWhenWeaved = "";
	
	protected String mFilePath;
	
	protected Map<String, String> mBookSettingsMap;
	protected List<String[]> mPossibleWaevedSources;

	public int getBookID() { return mBookUID; }
	public void setBookID(int uid) { mBookUID = uid; }
	
	public String getUID() { return String.valueOf(mBookUID); }
	
	public String getPath() { return mFilePath; }
	public void setPath(String filePath) { mFilePath=filePath; }
	
	public String getDisplayName() { return mDisplayName; }
	public void setDisplayName(String dispName) { mDisplayName = dispName; }
	
	public void setContents(BookContents bookContents) {mContents = bookContents; }
	public BookContents getContents() {return mContents; }

	public String toString() {return mDisplayName;}
	
	public Map<String, String> getSettings() { return mBookSettingsMap; }

	public void setBookSettingsMap(Map<String, String> mBookSettingsMap) { this.mBookSettingsMap = mBookSettingsMap; }
	
	//Compare by id
	public int compareTo(Book other) 
	{
		return (this.getBookID() - other.getBookID());
	}
	
	public void buildSearchIndex() {
		// TODO Auto-generated method stub
		
	}
	public String getPureText() {
		// TODO Auto-generated method stub
		return null;
	}
	public ISortedNumberList getChapterMap() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<String[]> getPossibleWaevedSources() 
	{
		return mPossibleWaevedSources;
	}
	
	public void setWaevedSources(List<String[]> mWaevedSources) 
	{
		this.mPossibleWaevedSources = mWaevedSources;
	}
	
	public String getDisplayNameWhenWeaved() 
	{
		if (!mDisplayNameWhenWeaved.isEmpty()) return mDisplayNameWhenWeaved;
		
		return mDisplayName;
	}
	
	public void setDisplayNameWhenWeaved(String mDisplayNameWhenWeaved) 
	{
		this.mDisplayNameWhenWeaved = mDisplayNameWhenWeaved;
	}
}
