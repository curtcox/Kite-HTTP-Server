package mite.ast

/**
 * Uses reflection to render any list.
 */
class ReflectionRenderer : Node.Renderer {

    override fun header(list: List<*>): List<String> = ClassSpecificReflectionRenderer(classOf(list)).header()

    override fun render(node: Node): List<String> = ClassSpecificReflectionRenderer(node.value::class).render(node)

    private fun classOf(list: List<*>) = when {
        list.isEmpty()       -> String::class
        list.first() is Node -> (list.first() as Node).value::class
        else                 -> list.first()!!::class
    }
}