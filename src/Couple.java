public class Couple {
    
    Server server;
    Video video;

    public Couple(Server s, Video v) {
        server = s;
        video = v;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Couple)) {
            return false;
        }

        Couple c = (Couple) o;

		return (server.id == c.server.id) && (video.id == c.video.id);
    }

    @Override
    public int hashCode() {
        return 17 * server.id + 31 * video.id;
    }

    public float score() {
        return video.scoreServer(server);
    }

    public void putVideo() {
        server.putVideo(video);
    }

}
