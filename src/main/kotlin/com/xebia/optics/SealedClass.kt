package com.xebia.com.xebia.optics

import arrow.core.identity
import arrow.core.toOption
import arrow.optics.Lens
import arrow.optics.Prism

/**
 * Sealed classes exists out of 0..n subtypes,
 * and require `when` (pattern matching) to work with any of the concrete subtypes.
 * So when we're working with large structures, that contain sealed classes,
 * it can become very annoying to manually write this boilerplate.
 *
 * There we want to implement a [Lens] for common properties,
 * such that we can avoid dealing with concrete subtypes all together.
 *
 * And we want to implement a [Prism], that allows us to 'focus' on certain subtypes.
 * This will allow us to add pattern matching into our Optics paths.
 *
 * Steps:
 *  1. Implement `property: Lens`.
 *  2. Implement `nullable: Lens`.
 */
sealed class SealedClass {
    data class Point(val x: Int, val y: Int) : SealedClass()
    data class Point3D(val x: Int, val y: Int, val z: Int) : SealedClass()

    companion object {
        // TODO, implement point.
        val point: Prism<SealedClass, Point> =
            Prism({ (it as? Point).toOption() }, ::identity)

        // TODO, implement x for common property x
        val x: Lens<SealedClass, Int> =
            Lens({ s ->
                when (s) {
                    is Point -> s.x
                    is Point3D -> s.x
                }
            }) { s, x ->
                when (s) {
                    is Point -> s.copy(x = x)
                    is Point3D -> s.copy(x = x)
                }
            }
    }
}
