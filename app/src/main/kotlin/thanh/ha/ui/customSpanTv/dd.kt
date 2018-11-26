package thanh.ha.ui.customSpanTv

//        val openSyncTax = '['
//        val closedSyncTax = ']'
//        for (char in text) {
//
//            val spanStart: Int
//            var startLine = 0
//
//            val spanEnd: Int
//            var endLine = 0
//
//            var startOffset = 0
//            var endOffset = 0
//
//            if (char == openSyncTax) {
//                spanStart = text.indexOf(char)
//                startLine = layout.getLineForOffset(spanStart)
//                startOffset = (layout.getPrimaryHorizontal(spanStart)
//                        + -1 * layout.getParagraphDirection(startLine) * horizontalPadding).toInt()
//            }
//            if (char == closedSyncTax) {
//                spanEnd = text.indexOf(char)
//                endLine = layout.getLineForOffset(spanEnd)
//                endOffset = (layout.getPrimaryHorizontal(spanEnd)
//                        + -1 * layout.getParagraphDirection(endLine) * horizontalPadding).toInt()
//            }
//            val renderer = if (startLine == endLine)
//                singleLineRenderer
//            else multiLineRenderer
//            renderer.draw(canvas, layout, startLine, endLine, startOffset, endOffset)
//        }