package com.example.playerui;

public enum View {
    LIST_PLAYLISTS("list_playlists-view.fxml"),
    PLAYLIST("playlist-view.fxml"),
    ADD_PLAYLIST_FORM("addPlaylistForm-view.fxml");

    private String fileName;

    View(String fileName){
        this.fileName = fileName;
    }

    public String getFileName(){
        return fileName;
    }
}
