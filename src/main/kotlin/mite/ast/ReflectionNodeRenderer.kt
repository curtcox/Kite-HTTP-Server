package mite.ast

/**
 * Uses reflection to render any list.
 */
object ReflectionNodeRenderer : Node.Renderer {

    override fun header(list: List<*>): List<String> = ClassSpecificReflectionNodeRenderer(classOf(list)).header()

    override fun render(node: Node): List<String> = ClassSpecificReflectionNodeRenderer(node.value::class).render(node)

    private fun classOf(list: List<*>) = when {
        list.isEmpty()       -> String::class
        list.first() is Node -> (list.first() as Node).value::class
        else                 -> list.first()!!::class
    }
}