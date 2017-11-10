import java.net.*;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

class Download extends Observable implements Runnable {

	// Max size of download buffer.
	private static final int MAX_BUFFER_SIZE = 1024;

	// These are the status names.
	public static final String STATUSES[] = {"Downloading",
	"Paused", "Complete", "Cancelled", "Error"};

	// These are the status codes.
	public static final int DOWNLOADING = 0;
	public static final int PAUSED = 1;
	public static final int COMPLETE = 2;
	public static final int CANCELLED = 3;
	public static final int ERROR = 4;

	private URL url; // download URL
	private int size; // size of download in bytes
	private int downloaded; // number of bytes downloaded
	private int status; // current status of download
	private long startTime;	//Start time of the download

	// Constructor for Download.
	public Download(URL url) {
		startTime = System.currentTimeMillis();
	    this.url = url;
	    size = -1;
	    downloaded = 0;
	    status = DOWNLOADING;

	    // Begin the download.
	    download();
	}

	// Get this download's URL.
	public String getUrl() {
	    return url.toString();
	}

	// Get this download's size.
	public int getSize() {
	    return size;
	}

	// Get this download's progress.
	public float getProgress() {
	    return ((float) downloaded / size) * 100;
	}

	// Get this download's status.
	public int getStatus() {
	    return status;
	}
	
	public double getSpeed() {
		long currentTime = System.currentTimeMillis()-startTime;
		return (((double)downloaded / currentTime));
	}

	// Pause this download.
	public void pause() {
	    status = PAUSED;
	    stateChanged();
	}

	// Resume this download.
	public void resume() {
	    status = DOWNLOADING;
	    stateChanged();
	    download();
	}

	// Cancel this download.
	public void cancel() {
	    status = CANCELLED;
	    stateChanged();
	}

	// Mark this download as having an error.
	private void error() {
	    status = ERROR;
	    stateChanged();
	}

	// Start or resume downloading.
	private void download() {
	    Thread thread = new Thread(this);
	    thread.start();
	}

	// Get file name portion of URL.
	private String getFileName(URL url) {
	    String fileName = url.getFile();
	    return fileName.substring(fileName.lastIndexOf('/') + 1);
	}

	// Download file.
	public void run() {
	    RandomAccessFile file = null;
	    InputStream stream = null;

	    try {
	        // Open connection to URL.
	        HttpURLConnection connection =
	                (HttpURLConnection) url.openConnection();

	        // Specify what portion of file to download.
	        connection.setRequestProperty("Range",
	                "bytes=" + downloaded + "-");

	        // Connect to server.
	        connection.connect();

	        // Make sure response code is in the 200 range.
	        if (connection.getResponseCode() / 100 != 2) {
	            error();
	        }

	        // Check for valid content length.
	        int contentLength = connection.getContentLength();
	        if (contentLength < 1) {
	            error();
	        }

	  /* Set the size for this download if it
	     hasn't been already set. */
	        if (size == -1) {
	            size = contentLength;
	            stateChanged();
	        }

	        // Open file and seek to the end of it.
	        file = new RandomAccessFile(getFileName(url), "rw");
	        file.seek(downloaded);

	        stream = connection.getInputStream();
	        while (status == DOWNLOADING) {
	    /* Size buffer according to how much of the
	       file is left to download. */
	            byte buffer[];
	            if (size - downloaded > MAX_BUFFER_SIZE) {
	                buffer = new byte[MAX_BUFFER_SIZE];
	            } else {
	                buffer = new byte[size - downloaded];
	            }

	            // Read from server into buffer.
	            int read = stream.read(buffer);
	            if (read == -1)
	                break;

	            // Write buffer to file.
	            file.write(buffer, 0, read);
	            downloaded += read;
	            stateChanged();
	        }

	  /* Change status to complete if this point was
	     reached because downloading has finished. */
	        if (status == DOWNLOADING) {
	            status = COMPLETE;
	            stateChanged();
	        }
	    } catch (Exception e) {
	        error();
	    } finally {
	        // Close file.
	        if (file != null) {
	            try {
	                file.close();
	            } catch (Exception e) {}
	        }

	        // Close connection to server.
	        if (stream != null) {
	            try {
	                stream.close();
	            } catch (Exception e) {}
	        }
	    }
	}

	// Notify observers that this download's status has changed.
	private void stateChanged() {
	    setChanged();
	    notifyObservers();
	}
}

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		URL website = new URL("http://speedtest.ftp.otenet.gr/files/test1Mb.db");
		Download d = new Download(website);
		long total = d.getSize();
		long current = (long)(d.getProgress()*total);
		long startTime = System.currentTimeMillis();
		double s=-1;
		while(true) {
			double x = d.getProgress();
			Thread.sleep(100);
			if(x==s)
				continue;
			if(d.getSize()!=-1 && d.getProgress() > 0)
				printProgress(startTime,d.getSize(),(long)(d.getProgress()*d.getSize()/100),d.getSpeed());
			s = x;
			if (x==100)
				break;
		}
		System.out.println("\nDownload Complete, Total Time:" + TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()-startTime)+"s @ "+d.getSpeed()+"KBPS");
	}


	private static void printProgress(long startTime, long total, long current,double speed) {
	    long eta = current == 0 ? 0 : 
	        (total - current) * (System.currentTimeMillis() - startTime) / current;
	
	    String etaHms = current == 0 ? "N/A" : 
	            String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(eta),
	                    TimeUnit.MILLISECONDS.toMinutes(eta) % TimeUnit.HOURS.toMinutes(1),
	                    TimeUnit.MILLISECONDS.toSeconds(eta) % TimeUnit.MINUTES.toSeconds(1));
	
	    StringBuilder string = new StringBuilder(140);   
	    int percent = (int) (current * 100 / total);
	    string
	        .append('\r')
	        .append(String.join("", Collections.nCopies(percent == 0 ? 2 : 2 - (int) (Math.log10(percent)), " ")))
	        .append(String.format(" %d%% [", percent))
	        .append(String.join("", Collections.nCopies(percent, "=")))
	        .append('>')
	        .append(String.join("", Collections.nCopies(100 - percent, " ")))
	        .append(']')
	        .append(String.join("", Collections.nCopies((int) (Math.log10(total)) - (int) (Math.log10(current)), " ")))
	        .append(String.format(" %d/%d, ETA: %s @ %.2fKBPS", current, total, etaHms,speed));
	
	    System.out.print(string);
	}
}