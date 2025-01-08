package com.example.wrappedback.web;

public class ArtistDetails {

    private String artist;
    private Integer ms;
    private Integer timesPlayed;

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
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
        return "ArtistDetails [artist=" + artist + ", ms=" + ms + ", timesPlayed=" + timesPlayed + "]";
    }

}
