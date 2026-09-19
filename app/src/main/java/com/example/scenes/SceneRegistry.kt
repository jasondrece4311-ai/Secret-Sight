package com.example.scenes

import com.example.model.SceneDefinition

object SceneRegistry {
    private val scenesList = mutableListOf<SceneDefinition>(
        SceneData.scene1,
        SceneData.scene2,
        SceneData.scene3
    ).apply {
        addAll(AllScenesData.additionalScenes)
        sortBy { it.id }
    }

    fun getAllScenes(): List<SceneDefinition> = scenesList.toList()

    fun getSceneById(id: Int): SceneDefinition? = scenesList.find { it.id == id }

    fun getTotalScenesCount(): Int = scenesList.size
}
