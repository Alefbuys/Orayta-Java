package download;

import java.util.ArrayList;

import download.ISingleFileDownloader.DownloadStatus;

/*
 * Manages a list of files to download. 
 * All files will be downloaded asynchronously (each in a new thread), but a few at a time.
 */

public class DownloadManager implements IDownloadManager
{
	private static final int MAX_PARRALLEL_DOWNLOADS = 3;

	ArrayList<String> pendingURLs = new ArrayList<String>();
	ArrayList<String> pendingSavePaths = new ArrayList<String>();
	
	int totalProgress;
	
	ArrayList<ISingleFileDownloader> activeDownloads = new ArrayList<ISingleFileDownloader>();
	//These listen to events of each download
	ArrayList<IDownloadListener> internallListeners = new ArrayList<IDownloadListener>();
	//These listen to events of the Manager itself. So they are called when ALL downloads are finished,
	// or to give the status of the total progress
	ArrayList<IDownloadListener> externallProgressListeners = new ArrayList<IDownloadListener>();
	ArrayList<IDownloadListener> externallFinishedListeners = new ArrayList<IDownloadListener>();
	
	private class DownloadListener implements IDownloadListener
	{
		ISingleFileDownloader mDownloader;
		
		public void registerToEvents()
		{
			mDownloader.registerFinishedListener(this);
			mDownloader.registerProgressListener(this);
		}
		
		public DownloadListener(ISingleFileDownloader downloader)
		{
			mDownloader = downloader;
		}


		public void onDownloadProgress(int percent) 
		{
			updateProgress();
		}

		public void onDownloadFinished(DownloadStatus status) 
		{
			downloadFinished(mDownloader, status);
		}
		
	}
	
	public void addDownloadRequest(String urlPath, String savePath) 
	{
		pendingURLs.add(urlPath);
		pendingSavePaths.add(savePath);
		
		StartMoreDownloads();
	}

	private void StartMoreDownloads()
	{
		//This is just to avoid a while...
		for (int i=0; i<MAX_PARRALLEL_DOWNLOADS; i++)
		{
			if (activeDownloads.size() < MAX_PARRALLEL_DOWNLOADS)
			{
				if (pendingURLs.size() > 0) StartNextDownload();
			}
		}
	}
	
	private void StartNextDownload()
	{
		if (pendingURLs.size() > 0)
		{
			String urlPath = pendingURLs.remove(0);
			String savePath = pendingSavePaths.remove(0);
			
			//Create a new downloader:
			ISingleFileDownloader downloader = new ProgressedFileDownload();
			downloader.downloadAsync(urlPath, savePath, true);
			activeDownloads.add(downloader);
			
			DownloadListener d = new DownloadListener(downloader);
			internallListeners.add(d);
			d.registerToEvents();
			
			updateProgress();
		}
	}
	
	private void updateProgress() 
	{
		//If all downloads are done, return 100%
		totalProgress = 100;
		
		long totalSize = 0;
		long totalWeight = 0;
		
		for (ISingleFileDownloader d:activeDownloads)
		{
			totalSize += d.getDownloadSize();
			totalWeight += d.getDownloadSize() * d.getProgress();
		}
		
		if (totalSize != 0) totalProgress = (int)((float)totalWeight / totalSize);
		
		for (IDownloadListener l:externallProgressListeners)
		{
			l.onDownloadProgress(totalProgress);
		}
	}

	private void downloadFinished(ISingleFileDownloader downloader, DownloadStatus status)
	{
		activeDownloads.remove(downloader);
		StartMoreDownloads();
		
		//All downloads finished
		if (activeDownloads.size() == 0)
		{
			for (IDownloadListener l:externallFinishedListeners)
			{
				l.onDownloadFinished(status);
			}
		}
	}

	public void registerProgressListener(IDownloadListener listener) {
		externallProgressListeners.add(listener);
	}

	public void registerFinishedListener(IDownloadListener listener) {
		externallFinishedListeners.add(listener);
	}
}
