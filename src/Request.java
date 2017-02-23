
public class Request {
	
	int size;
	Video video;
	Endpoint endpoint;
	
	Request(int size, Video video, Endpoint endpoint){
		this.size = size;
		this.video = video;
		this.endpoint = endpoint;
	}
}
