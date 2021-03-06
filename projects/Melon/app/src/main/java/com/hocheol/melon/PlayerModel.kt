package com.hocheol.melon

data class PlayerModel(
    private val playMusicList: List<MusicModel> = emptyList(),
    var currentPosition: Int = -1,
    var isWatchingPlayListview: Boolean = true
) {

    fun getAdapterModels(): List<MusicModel> =
        playMusicList.mapIndexed { index, musicModel ->
            val newItem = musicModel.copy(
                isPlaying = index == currentPosition
            )
            newItem
        }

    fun updateCurrentPosition(musicModel: MusicModel) {
        currentPosition = playMusicList.indexOf(musicModel)
    }

    fun nextMusic(): MusicModel? {
        if (playMusicList.isEmpty()) return null

        currentPosition = if ((currentPosition + 1) == playMusicList.size) 0 else currentPosition + 1

        return playMusicList[currentPosition]
    }

    fun prevMusic(): MusicModel? {
        if (playMusicList.isEmpty()) return null

        currentPosition = if ((currentPosition - 1) < 0) playMusicList.lastIndex else currentPosition - 1

        return playMusicList[currentPosition]
    }

    fun currentMusicModel(): MusicModel? =
        if (playMusicList.isEmpty()) null else playMusicList[currentPosition]

}