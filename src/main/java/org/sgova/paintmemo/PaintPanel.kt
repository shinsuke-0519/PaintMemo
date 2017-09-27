package org.sgova.paintmemo

import org.sgova.paintmemo.mode.Figure
import org.sgova.paintmemo.mode.RectangleFigure
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.event.MouseMotionListener
import javax.swing.JPanel

class PaintPanel() : JPanel(), MouseListener, MouseMotionListener {
	
	val paper = Paper()
	
	var drawingFigure: Figure? = null
		private set
	
	var createFigure = "rectangle"
	var currentColor: Color = Color.BLACK
	
	
	init {
		setDoubleBuffered(true)
		
		addMouseListener(this)
		addMouseMotionListener(this)
		
	}
	
	override fun paintComponent(g: Graphics) {
		g.color = Color.WHITE
		g.fillRect(0, 0, width, height)
		
		// すべての図形を描画
		paper.figures.forEach {
			paintFigure(g, it)
		}
		
		// 現在描画している図形をプレビュー
		if (drawingFigure != null) {
			paintFigure(g, drawingFigure!!)
		}
	}
	
	// Figureを描画
	fun paintFigure(g: Graphics, figure: Figure) {
		g.color = figure.color
		figure.reshape(g)
	}
	
	override fun mouseReleased(e: MouseEvent) {
		if (drawingFigure != null) {
			paper.pushFigure(drawingFigure!!)
			
			drawingFigure = null
			repaint()
		}
	}
	
	override fun mouseDragged(e: MouseEvent) {
		drawingFigure?.x = e.x
		drawingFigure?.y = e.y
		
		repaint()
	}

	override fun mousePressed(e: MouseEvent) {
		// 図形を書いていなければ図形を生成
		if (drawingFigure == null) {
			drawingFigure = when (createFigure) {
				"rectangle" -> RectangleFigure(e.x, e.y, currentColor)
				else -> RectangleFigure(e.x + 50, e.y, currentColor)
			}
		}
	}
	
	override fun mouseClicked(e: MouseEvent) {}
	override fun mouseEntered(e: MouseEvent) {}
	override fun mouseExited(e: MouseEvent) {}
	
	override fun mouseMoved(e: MouseEvent) {}
}