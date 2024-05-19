package com.example.playerui;

public enum View {
    LIST_PLAYLISTS("listPlaylists-view.fxml"),
    PLAYLIST("playlist-view.fxml"),
    ADD_PLAYLIST_FORM("addPlaylistForm-view.fxml"),
    EDIT_PLAYLIST("editPlaylist-view.fxml"),
    LIST_SONGS("listSongs-view.fxml");

    private String fileName;

    View(String fileName){
        this.fileName = fileName;
    }

    public String getFileName(){
        return fileName;
    }
}
