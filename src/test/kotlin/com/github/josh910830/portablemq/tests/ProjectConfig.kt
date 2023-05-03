package com.github.josh910830.portablemq.tests

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.spec.IsolationMode
import io.kotest.extensions.spring.SpringExtension

class ProjectConfig : AbstractProjectConfig() {

    override val isolationMode: IsolationMode = IsolationMode.InstancePerLeaf
    override fun extensions() = listOf(SpringExtension)

}
