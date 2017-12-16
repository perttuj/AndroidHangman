package server.common;

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * Created by Perttu on 2017-12-14.
 */

public class ServerInfoDTO implements Serializable {

    private String response;
    private int score;
    private int guesses;
    private String current;
    private boolean status;

    public ServerInfoDTO(String res, int s, int g) {
        this.response = res;
        this.score = s;
        this.guesses = g;
    }

    public static String toString(ServerInfoDTO dto) {
        StringJoiner joiner = new StringJoiner(Constants.INFO_DELIMETER);
        joiner.add(dto.getResponse());
        joiner.add(dto.getScore());
        joiner.add(dto.getGuesses());
        joiner.add(dto.getCurrent());
        joiner.add(dto.getStatus());
        return joiner.toString();
    }
    public void setStatus(boolean status) { this.status = status; }
    public void setCurrent(String s) {
        this.current = s;
    }
    public void setResponse(String s) {
        this.response = s;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public void setGuesses(int guesses) {
        this.guesses = guesses;
    }
    public String getStatus() { return String.valueOf(this.status); }
    public String getCurrent() { return this.current; }
    public String getResponse() {
        return this.response;
    }
    public String getScore() {
        return String.valueOf(this.score);
    }
    public String getGuesses() {
        return String.valueOf(this.guesses);
    }
}
