package best.beside.ctrl.teambuilder.domain.valueobject

import org.springframework.data.domain.Page

data class PageResponse<T>(
    val items: List<T>,
    val metadata: PageMetadata,
) {
    companion object {
        fun <T> of(page: Page<T>): PageResponse<T> {
            return PageResponse(
                items = page.content,
                metadata = PageMetadata(
                    page = page.number,
                    size = page.size,
                    total = page.totalElements,
                )
            )
        }
    }

    data class PageMetadata(val page: Int, val size: Int, val total: Long)
}
