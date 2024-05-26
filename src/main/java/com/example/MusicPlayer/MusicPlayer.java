package com.example.MusicPlayer;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.*;
import java.util.*;

public class MusicPlayer {
    private final List<Song> songs;
    private final List<Playlist> playlists;
    private int playlistCount;
    private int currentSongId;
    private Song currentSong;
    private int currentPlaylistId;
    private Playlist currentPlaylist;
    private final File playlistsDir;
    private MediaPlayer player;


    public List<Playlist> getPlaylists() {
        return playlists;
    }
    public int getCurrentPlaylistId() { return currentPlaylistId; }
    public Playlist getCurrentPlaylist() { return currentPlaylist; }
    public Song getCurrentSong() {
        return currentSong;
    }
    public int getCurrentSongId() { return currentSongId; }
    public List<Song> getSongs() {
        return songs;
    }

    public List<Integer> getSongsId(){
        List<Integer> songsId = new ArrayList<>();
        for (Song song : songs){
            songsId.add(song.getId());
        }
        return songsId;
    }

    public MusicPlayer(){
        this.songs = new ArrayList<>();
        this.playlists = new ArrayList<>();
        this.playlistCount = 0;
        this.currentSongId = -1;
        this.currentPlaylistId = -1;
        this.playlistsDir = new File(Objects.requireNonNull(getClass().getResource("/playlists")).getPath());
        loadSongsFromDir(Objects.requireNonNull(getClass().getResource("/music/text")).getPath());
        loadPlaylistsFromDir(Objects.requireNonNull(getClass().getResource("/playlists")).getPath());
    }

    public int playPlaylist(int playlistId){
        currentPlaylistId = playlistId;
        currentPlaylist = getPlaylistById(playlistId);
        if (currentPlaylist == null){
            return 1;
        }
        playSong(currentPlaylist.getFirstSongId());
        return 0;
    }

    public void playPlaylistSong(int playlistId, int songId){
        currentPlaylistId = playlistId;
        currentPlaylist = getPlaylistById(playlistId);
        currentSongId = songId;
        currentSong = getSongById(songId);
        if (currentPlaylist == null || currentSong == null || !currentPlaylist.getSongsId().contains(currentSongId)){
            return;
        }
        //playPlaylist(currentPlaylistId);
        playSong(currentSongId);
    }

    public int playSong(int songId){
        currentSongId = songId;
        currentSong = getSongById(songId);
        if (currentSong == null){
            return 1;
        }
        if (player != null){
            player.stop();
        }

        Media sound = new Media(new File(currentSong.getFilePath()).toURI().toString());
        player = new MediaPlayer(sound);
        player.play();
        return 0;
    }

    public void playOrStopSong(){
        if (currentSong == null){
            return;
        }
        if (player == null){
            return;
        }
        if (player.getStatus() == MediaPlayer.Status.PLAYING){
            player.pause();
            return;
        }
        if (player.getStatus() == MediaPlayer.Status.PAUSED){
            player.play();
        }
    }

    public int playNext(){
        if (currentPlaylist == null){
//            System.out.println("Сначала выберите плейлист");
            return 1;
        }
        currentSongId = currentPlaylist.getNextSongId(currentSongId);
        currentSong = getSongById(currentSongId);
        playSong(currentSongId);
        return 0;
    }

    public int playPrev(){
        if (currentPlaylist == null){
            return 1;
        }
        currentSongId = currentPlaylist.getPrevSongId(currentSongId);
        currentSong = getSongById(currentSongId);
        playSong(currentSongId);
        return 0;
    }

    public void addPlaylist(String name) {
        Playlist playlist = new Playlist(playlistCount + 1, name);
        playlists.add(playlist);
        playlistCount++;
    }

    public void addPlaylist(String name, List<Song> songs) {
        Playlist playlist = new Playlist(playlistCount + 1, name);
        for (Song song : songs){
            playlist.addSong(song.getId());
        }
        playlists.add(playlist);
        playlistCount++;
    }


    public int deletePlaylistById(int id){
        for (Playlist playlist : playlists){
            if (playlist.getId() == id){
                playlists.remove(playlist);
                playlistCount--;
                return 0;
            }
        }
        return 1;
    }

    public void deleteAllPlaylistsById(List<Integer> ids){
        for (int id : ids){
            deletePlaylistById(id);
        }
    }

    public int addSongToPlaylistById(int playlistId, int songId){
        Playlist playlist = getPlaylistById(playlistId);
        if (playlist == null){
            return 1;
        }

        Song song = getSongById(songId);
        if (song == null){
            return 2;
        }
        else{
            playlist.addSong(songId);
            return 0;
        }
    }

    public void deleteAllSongsFromPlaylistByIds(List<Integer> songsIds, int playlistId){
        for (int songId : songsIds){
            deleteSongFromPlaylistById(playlistId, songId);
        }
    }

    public int deleteSongFromPlaylistById(int playlistId, int songId){
        Playlist playlist = getPlaylistById(playlistId);
        if (playlist != null){
            if (!playlist.deleteSong(songId)){
                return 2;
            }
            else {
                return 0;
            }
        }
        else {
            return 1;
        }
    }

    public void loadSongsFromDir(String path) {
        File folder = new File(path);

        File[] files = folder.listFiles();
        int songsNumber = 0;

        if (files != null){
            for (File file : files){
                songs.add(loadSongFromFile(file, songsNumber + 1));
                songsNumber++;
            }
        }
    }

    public Song loadSongFromFile(File file, int songId) {
        try (FileReader fileReader = new FileReader(file);
             BufferedReader reader = new BufferedReader(fileReader))
        {
            return new Song(songId, reader.readLine(), reader.readLine(), reader.readLine(), reader.readLine());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public File getPlaylistFile(int playlistId){
        File[] files = playlistsDir.listFiles();
        if (files != null){
            for (File file : files){
                if (file.getName().equals(String.format("%d.txt", playlistId))){
                    return file;
                }
            }
        }
        return null;
    }

    public Playlist loadPlaylistFromFile(File file){
        if (file != null){
            try (FileReader fileReader = new FileReader(file);
                 BufferedReader reader = new BufferedReader(fileReader))
            {
                Playlist playlist = new Playlist(Integer.parseInt(reader.readLine()), reader.readLine());
                String curSongId = reader.readLine();
                List<Integer> songs = new ArrayList<>();
                while(curSongId != null){
                    songs.add(Integer.parseInt(curSongId));
                    curSongId = reader.readLine();
                }
                playlist.songsId = songs;
                return playlist;
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public void loadPlaylistsFromDir(String path){
        File folder = new File(path);

        File[] files = folder.listFiles();

        if (files != null){
            for (File file : files){
                playlists.add(loadPlaylistFromFile(file));
                playlistCount++;
            }
        }
    }

    public int savePlaylistToFile(int playlistId) {
        Playlist playlist = getPlaylistById(playlistId);
        try (FileWriter writer = new FileWriter(String.format("%s\\%s.txt", playlistsDir, playlist.getName()))){
            writer.write(Integer.toString(playlistId));
            writer.write('\n');
            writer.write(playlist.getName());
            writer.write('\n');
            for (int songId : playlist.getSongsId()){
                writer.write(Integer.toString(songId));
                writer.write('\n');
            }
            return 0;
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
            return 1;
        }
    }

    public void printAllSongs(){
        if (songs == null){
            System.out.println("Список песен пуст");
            return;
        }

        System.out.println("Список всех песен.");
        for(Song song : songs)
        {
            System.out.println(song.toString());
        }
    }

    public void printAllPlaylists(){
        if (playlists.isEmpty()){
            System.out.println("Список плейлистов пуст");
            return;
        }
        System.out.println("Список всех плейлистов.");
        for(Playlist playlist : playlists){
            System.out.printf("%d. %s\n%n", playlist.getId(), playlist.getName());
        }
    }

    public Playlist getPlaylistById(int id){
        for (Playlist playlist : playlists){
            if (playlist.getId() == id){
                return playlist;
            }
        }
        return null;
    }

    public Song getSongById(int id){
        for (Song song : songs){
            if (song.getId() == id){
                return song;
            }
        }
        return null;
    }

    public List<Integer> getSongsNotInPlaylistId(int playlistId){
        List<Integer> playlistSongs = getPlaylistById(playlistId).getSongsId();
        List<Integer> notInPlaylistSongs = getSongsId();
        for (int playlistSongId : playlistSongs){
            notInPlaylistSongs.remove(playlistSongId);
        }
        return notInPlaylistSongs;
    }

    public List<Song> getNotInPlaylistSongs(int playlistId){
        List<Integer> playlistSongs = getPlaylistById(playlistId).getSongsId();
        List<Song> allSongs = getSongs();
        List<Song> notInPlaylistSongs = new ArrayList<Song>(allSongs);
        //Collections.copy(allSongs, notInPlaylistSongs);
        for (int playlistSongId : playlistSongs){
            notInPlaylistSongs.remove(getSongById(playlistSongId));
        }
        return notInPlaylistSongs;
    }


    public void start(Scanner scanner){

        String playerCommands =
                """
                        Список команд:
                          1 - Показать список песен
                          2 - Включить предыдущий трек
                          3 - Включить следующий трек
                          4 - Повторить текущий трек
                          5 - Плейлисты
                          0 - Выйти
                        """;

        String playlistsCommands =
                """
                          1 - Показать список плейлистов
                          2 - Cоздать плейлист
                          3 - Включить плейлист
                          4 - Сохранить плейлист
                          5 - Загрузить плейлист
                          6 - Удалить плейлист
                          7 - Добавить песню в плейлист
                          8 - Показать весь плейлист
                          9 - Убрать песню из плейлиста
                          -1 - Назад
                          0 - Выйти
                        """;

        Playlist playlist;
        int playlistId;
        int songId;
        int code;

        System.out.println(playerCommands);
        int command;
        do {
            System.out.print("Команда = ");
            command = scanner.nextInt();
            switch (command) {
                case 1:
                    printAllSongs();
                    break;
                case 2:
                    code = playPrev();
                    if (code == 1){
                        System.out.println("Сначала выберите плейлист");
                    }
                    break;

                case 3:
                    code = playNext();
                    if (code == 1){
                        System.out.println("Сначала выберите плейлист");
                    }
                    break;

                case 4:
                    code = playSong(currentSongId);
                    if (code == 1){
                        System.out.println("Сначала включите песню");
                    }
                    break;

                case 5:
                    System.out.println(playlistsCommands);
                    do {
                        System.out.print("Команда = ");
                        command = scanner.nextInt();
                        switch (command) {
                            case 1:
                                printAllPlaylists();
                                break;
                            case 2:
                                System.out.println("Введите название плейлиста: ");
                                String name = scanner.next();
                                addPlaylist(name);
                                break;
                            case 3:
                                System.out.println("Введите id плейлиста: ");
                                playlistId = scanner.nextInt();
                                code = playPlaylist(playlistId);
                                if (code == 1){
                                    System.out.println("Данный плейлист не найден");
                                }
                                command = -1;
                                break;

                            case 4:
                                System.out.println("Введите id плейлиста: ");
                                playlistId = scanner.nextInt();
                                code = savePlaylistToFile(playlistId);
                                if (code == 1){
                                    System.out.println("Данный плейлист не найден");
                                }
                                break;
                            case 5:
                                System.out.println("Введите id плейлиста: ");
                                playlistId = scanner.nextInt();
                                playlist = loadPlaylistFromFile(getPlaylistFile(playlistId));
                                if (playlist != null){
                                    playlists.add(playlist);
                                    playlistCount++;
                                }
                                else{
                                    System.out.println("Данный плейлист не найден");
                                }
                                break;

                            case 6:
                                System.out.println("Введите id плейлиста: ");
                                playlistId = scanner.nextInt();
                                code = deletePlaylistById(playlistId);
                                if (code == 0) {
                                    System.out.printf("Плейлист с id = %d успешно удален!%n", playlistId);
                                }
                                else if (code == 1) {
                                    System.out.printf("Плейлист с id = %d не найден!%n", playlistId);
                                }
                                break;

                            case 7:
                                System.out.println("Введите id плейлиста: ");
                                playlistId = scanner.nextInt();
                                System.out.println("Введите id песни: ");
                                songId = scanner.nextInt();
                                code = addSongToPlaylistById(playlistId, songId);
                                if(code == 0){
                                    System.out.printf("Песня с id = %d успешно добавлена в плейлист с id = %s%n", songId, playlistId);
                                }
                                else if (code == 1){
                                    System.out.printf("Не удалось найти плейлист с id = %d%n", playlistId);
                                }
                                else if(code == 2) {
                                    System.out.printf("Не удалось найти песню с id = %d%n", songId);
                                }
                                break;

                            case 8:
                                System.out.println("Введите id плейлиста: ");
                                playlistId = scanner.nextInt();
                                playlist = getPlaylistById(playlistId);
                                if (playlist != null){
                                    System.out.println(getPlaylistById(playlistId).toString());
                                }
                                else {
                                    System.out.printf("Плейлист с id = %d не найден%n", playlistId);
                                }
                                break;

                            case 9:
                                System.out.println("Введите id плейлиста: ");
                                playlistId = scanner.nextInt();
                                System.out.println("Введите id песни: ");
                                songId = scanner.nextInt();

                                code = deleteSongFromPlaylistById(playlistId, songId);
                                if (code == 0){
                                    System.out.printf("Песня с id = %d успешно удалена из плейлиста с id = %d%n", songId, playlistId);
                                }
                                else if (code == 1){
                                    System.out.printf("Не удалось найти плейлист с id = %d%n", playlistId);
                                }
                                else if (code == 2){
                                    System.out.printf("Не удалось найти песню с id = %d%n", songId);
                                }
                                break;
                        }
                    } while (command != 0 && command != -1);
                    break;
            }
        } while (command != 0);
    }
}