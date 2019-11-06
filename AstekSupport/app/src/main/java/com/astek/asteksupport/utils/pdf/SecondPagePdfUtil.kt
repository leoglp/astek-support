package com.astek.asteksupport.utils.pdf

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.text.TextPaint
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.closeDocument
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.createPage
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.drawText
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.footer
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.setTextOptions
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.writeInterviewPlan


@Suppress("DEPRECATION")
class SecondPagePdfUtil {

    companion object {

        private lateinit var canvas: Canvas
        private lateinit var textPaint: TextPaint
        private lateinit var pdfDocument: PdfDocument
        private lateinit var page: PdfDocument.Page

        @SuppressLint("StaticFieldLeak")
        private lateinit var activity: Activity


        fun createSecondPage(canvas: Canvas , textPaint: TextPaint , activity: Activity , pdfDocument: PdfDocument , page: PdfDocument.Page) {

            this.canvas = canvas
            this.textPaint = textPaint
            this.activity = activity
            this.pdfDocument = pdfDocument
            this.page = page

            createPage(2)
            footer("2")

            writeInterviewPlan()

            val firstTitle = "BILAN DES MISSIONS / PROJETS DE L’ANNEE ECOULEE"
            textPaint.set(setTextOptions(Color.YELLOW, Typeface.BOLD, 14F))
            drawText(firstTitle, 555, 20F, 160F,1.0F, false)

            val firstInfo = "Décrire succinctement les activités confiées, les compétences mises en œuvre et " +
                    "les résultats obtenus (Client, Période, Rôle occupé, Responsabilité, Environnement Technique et Fonctionnel)"
            textPaint.set(setTextOptions(Color.BLACK, Typeface.ITALIC, 12F))
            drawText(firstInfo, 555, 20F, 190F, 1.0F, false)

            closeDocument()
        }
    }

}