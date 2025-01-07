package com.example.wrappedback.web;

public class SongDetails {

    private String song;
    private Integer ms;
    private Integer timesPlayed;

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public Integer getMs() {
        return ms;
    }

    public void setMs(Integer ms) {
        this.ms = ms;
    }

    public Integer getTimesPlayed() {
        return timesPlayed;
    }

    public void setTimesPlayed(Integer timesPlayed) {
        this.timesPlayed = timesPlayed;
    }

    @Override
    public String toString() {
        return "SongDetails [song=" + song + ", ms=" + ms + ", timesPlayed=" + timesPlayed + "]";
    }

}
