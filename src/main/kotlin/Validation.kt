package com.xebia

import arrow.core.Either
import arrow.core.Nel
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.raise.zipOrAccumulate
import arrow.core.toNonEmptyListOrNull

sealed interface MediaValidationError
data object NoAuthors: MediaValidationError
data object EmptyAuthorName: MediaValidationError
data class DuplicateAuthor(val name: String): MediaValidationError
data object EmptyTitle: MediaValidationError

data class Author private constructor(val name: String) {
  companion object {
    operator fun invoke(name: String): Either<EmptyAuthorName, Author> = either {
      ensure(name.isNotEmpty()) { EmptyAuthorName }
      Author(name)
    }
  }
}

data class MediaInfo private constructor(val title: String, val authors: Set<Author>) {
  companion object {
    operator fun invoke(title: String, authors: List<String>): Either<Nel<MediaValidationError>, MediaInfo> = either {
      zipOrAccumulate(
        { ensure(title.isNotEmpty()) { EmptyTitle } ; title },
        {
          ensure(authors.isNotEmpty()) { NoAuthors }
          val authorSet = authors.mapOrAccumulate { Author(it).bind() }.toSet()
          val duplicateAuthors = authors.duplicates().toNonEmptyListOrNull()
          if (duplicateAuthors != null) {
            withNel { raise(duplicateAuthors.map(::DuplicateAuthor)) }
          }
          authorSet
        }
      ) { title, authors -> MediaInfo(title, authors) }
    }
  }
}

fun <A> Iterable<A>.duplicates(): Set<A> =
  groupBy { it }.filterValues { it.size > 1 }.keys
