package dev.ide.core

import java.io.File
import dev.ide.ui.backend.IdeBackend
import dev.ide.ui.backend.TreeNode
import dev.ide.ui.backend.ProjectInfo

// Временная упрощенная заглушка для гарантированной сборки билда
class IdeServicesBackend : IdeBackend {
    override val project: ProjectInfo = ProjectInfo("Stub", "", 0)
    override fun fileTree(): TreeNode = TreeNode("root", "root", dev.ide.ui.backend.NodeKind.Workspace, null)
    override fun readFile(path: String): String = ""
    override fun updateDocument(path: String, text: String) {}
    override fun saveFile(path: String, text: String) {}
    override fun runBuild() {}
    override val indexStatus = kotlinx.coroutines.flow.MutableStateFlow(dev.ide.ui.backend.IndexUiStatus())
    override val buildState = kotlinx.coroutines.flow.MutableStateFlow(dev.ide.ui.backend.BuildState())

    override fun deletePath(path: String): Boolean {
        return try {
            File(path).deleteRecursively()
        } catch (e: Exception) {
            false
        }
    }
}
