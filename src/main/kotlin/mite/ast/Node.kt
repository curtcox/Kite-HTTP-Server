package mite.ast

/**
 * A node in a tree of objects. Nodes can be maps, lists, or leaves.
 * Node trees exist for three reasons:
 * 1) To provide a generic internal object representation that can be rendered into HTML, JSON, etc..
 * 2) To make subsequent intermediate processing easier than it would be on the rendered form.
 * 3) To provide a regular mechanism of mapping URL paths into object paths like:
 *    /log@15@stack@3@class
 */
interface Node {

   val map   : Map<String,Node>
   val list  : List<Node>
   val leaf  : Any
   val value : Any
   val arity : Arity

    enum class Arity {
        list, map, leaf
    }

    /**
     * For rendering nodes.
     */
    interface Renderer {
        /**
         * Render header values to label the parts of a node value.
         */
        fun header(nodes:List<*>) : List<String>

        /**
         * Render the node component values that correspond to node headers for this node.
         */
        fun render(node:Node) : List<String>
    }

}