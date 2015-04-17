package book.contents;

import tree.IHasID;

public class ChapterID implements Comparable<ChapterID>, IHasID
{
	private BookID mBookId;
	private String mUID;
	private int mLevel = -1;
	
	public ChapterID(BookID bookId, String id)
	{
		mBookId = bookId;
		setID(id);
	}
	
	public ChapterID(BookID bookId)
	{
		mBookId = bookId;
	}
	
	public void setID(String id) { mUID = id.trim(); }
	
	public BookID getBookID() {
		return mBookId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + mBookId.getID();
		result = prime * result + ((mUID == null) ? 0 : mUID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		ChapterID other = (ChapterID) obj;
		if (mBookId != other.mBookId)
			return false;
		if (!mUID.equals(other.mUID))
			return false;
		
		return true;
	}
	
	public int hashcode()
	{
		 return mUID.hashCode();
	}
	
	public String toString()
	{
		 return "(Book: " + mBookId + ") - " + mUID;
	}

	public int compareTo(ChapterID o) 
	{
		return mUID.compareTo(o.getUID());
	}

	public String getUID() {
		return mUID;
	}
	
	public void setLevel(int level)
	{
		mLevel = level;
	}
	
	public int getLevel() {
		return mLevel;
	}
}
