package com.astek.asteksupport.utils.pdf

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.*
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Environment
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.graphics.withTranslation
import com.astek.asteksupport.R
import com.astek.asteksupport.utils.pdf.FirstPagePdfUtil.Companion.createFirstPage
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


@Suppress("DEPRECATION")
class PdfUtil {

    companion object {

        private lateinit var canvas: Canvas
        private lateinit var textPaint: TextPaint
        private lateinit var pdfDocument: PdfDocument
        private lateinit var page: PdfDocument.Page
        @SuppressLint("StaticFieldLeak")
        private lateinit var activity: Activity


        @RequiresApi(Build.VERSION_CODES.O)
        fun createPdf(activity: Activity) {

            this.activity = activity
            // create a new document
            pdfDocument = PdfDocument()

            //First page creation
            createFirstPage(canvas, textPaint, activity, pdfDocument, page)
        }



        private fun StaticLayout.draw(canvas: Canvas, x: Float, y: Float) {
            canvas.withTranslation(x, y) {
                draw(this)
            }
        }

        private fun writeHeader(){
            val logoHeader = activity.resources.getDrawable(R.drawable.header)
            val logoHeaderRect = Rect(0, 0, 595, 80)
            logoHeader.bounds = logoHeaderRect
            logoHeader.draw(canvas)
        }

        fun footer(pageNumber: String) {
            val totalPage = "8"
            val onText = "sur"
            val page = "page"

            val infoLine1 = "Entretien Annuel – Entretien Professionnel"
            val infoLine2 = "SMQS-000064-MOD_Entretien d'Evaluation et Professionnel Métier "
            val infoLine3 = "Confidentialité : DIFFUSION CONTROLEE"

            //Page
            textPaint.set(setTextOptions(Color.BLACK,Typeface.NORMAL,12F))
            drawText(page,700,430F,775F,1.0F,false)
            textPaint.set(setTextOptions(Color.BLACK,Typeface.BOLD,12F))
            drawText(pageNumber,700,465F,775F,1.0F,false)
            textPaint.set(setTextOptions(Color.BLACK,Typeface.NORMAL,12F))
            drawText(onText,700,480F,775F,1.0F,false)
            textPaint.set(setTextOptions(Color.BLACK,Typeface.BOLD,12F))
            drawText(totalPage,700,505F,775F,1.0F,false)

            //Info
            textPaint.set(setTextOptions(Color.BLACK,Typeface.NORMAL,10F))
            drawText(infoLine1,700,80F,775F,1.0F,false)
            textPaint.set(setTextOptions(Color.BLACK,Typeface.NORMAL,8F))
            drawText(infoLine2,700,80F,790F,1.0F,false)
            textPaint.set(setTextOptions(Color.BLACK,Typeface.NORMAL,8F))
            drawText(infoLine3,700,80F,800F,1.0F,false)
        }

        fun createPage(pageNumber: Int) {
            // create a page description
            val pageInfo: PdfDocument.PageInfo = PdfDocument.PageInfo.Builder(595, 842, pageNumber).create()

            // start a page
            page = pdfDocument.startPage(pageInfo)
            canvas = page.canvas
            textPaint = TextPaint()

            writeHeader()
        }

        fun writeTabInfo(){

            val title = "Légende de notation"
            val leftInfoTitle = "Evaluation de la Performance / Compétences :"
            val leftInfo = "1- Très Satisfaisant\n" +
                    "2 - Satisfaisant\n" +
                    "3 - Moyen\n" +
                    "4 – Insuffisant\n"

            val rightInfoTitle = "Evaluation de la maîtrise des Compétences :"
            val rightInfo = "A - Expertise\n" +
                    "B - Capacité à mettre en œuvre de manière autonome\n" +
                    "C - Capacité à mettre en œuvre partiellement\n" +
                    "D - Notion \n"


            //Title Rectangle
            var rect = Rect(80, 500, 515, 520)
            canvas.drawRect(rect, fillRectangleOptions(activity.getColor(R.color.green)))
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK,Typeface.BOLD,12F))
            drawText(title,435,80F,501F,1.0F,true)

            //Left Rectangle
            rect = Rect(80, 520, 298, 660)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.BOLD, 10F))
            drawText(leftInfoTitle,208,85F,525F,1.0F,false)
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 10F))
            drawText(leftInfo,208,85F,550F,1.3F,false)

            //Right Rectangle
            rect = Rect(298, 520, 515, 660)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.BOLD, 10F))
            drawText(rightInfoTitle,208,303F,525F,1.0F,false)
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 10F))
            drawText(rightInfo,208,303F,550F,1.3F,false)
        }


        fun writeInterviewPlan(){

            val title = "PLAN DE L'ENTRETIEN"
            val info1 = "Bilan Annuel"
            val info2 = "Objectifs N+1"
            val info3 = "Evolution"
            val info4 = "Formation"

            //Title Rectangle
            var rect = Rect(80, 100, 515, 120)
            canvas.drawRect(rect, fillRectangleOptions(activity.getColor(R.color.green)))
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK,Typeface.BOLD,12F))
            drawText(title,435,80F,102F,1.0F,true)

            //First Rectangle
            rect = Rect(80, 120, 189, 140)
            canvas.drawRect(rect, fillRectangleOptions(activity.getColor(R.color.orange)))
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.BOLD, 10F))
            drawText(info1,109,80F,122F,1.0F,true)

            //Second Rectangle
            rect = Rect(189, 120, 298, 140)
            canvas.drawRect(rect, fillRectangleOptions(activity.getColor(R.color.yellow)))
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 10F))
            drawText(info2,109,189F,122F,1.0F,true)

            //Third Rectangle
            rect = Rect(298, 120, 407, 140)
            canvas.drawRect(rect, fillRectangleOptions(activity.getColor(R.color.yellow)))
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 10F))
            drawText(info3,109,298F,122F,1.0F,true)

            //Fourth Rectangle
            rect = Rect(407, 120, 515, 140)
            canvas.drawRect(rect, fillRectangleOptions(activity.getColor(R.color.yellow)))
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 10F))
            drawText(info4,109,407F,122F,1.0F,true)
        }




        fun drawText(text: String, width: Int, x: Float, y: Float, spaceMultiline: Float, isCenter: Boolean){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if(isCenter) {
                    val staticLayout = StaticLayout.Builder
                        .obtain(text, 0, text.length, textPaint, width)
                        .setLineSpacing(0.0f, spaceMultiline)
                        .setAlignment(Layout.Alignment.ALIGN_CENTER)
                        .setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD)
                        .build()

                    staticLayout.draw(canvas,x,y)
                } else {
                    val staticLayout = StaticLayout.Builder
                        .obtain(text, 0, text.length, textPaint, width)
                        .setLineSpacing(0.0f, spaceMultiline)
                        .setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD)
                        .build()

                    staticLayout.draw(canvas,x,y)
                }
            } else {
                if(isCenter) {
                    val staticLayout = StaticLayout.Builder
                        .obtain(text, 0, text.length, textPaint, width)
                        .setLineSpacing(0.0f, spaceMultiline)
                        .setAlignment(Layout.Alignment.ALIGN_CENTER)
                        .build()

                    staticLayout.draw(canvas,x,y)
                } else {
                    val staticLayout = StaticLayout.Builder
                        .obtain(text, 0, text.length, textPaint, width)
                        .setLineSpacing(0.0f, spaceMultiline)
                        .build()

                    staticLayout.draw(canvas,x,y)
                }
            }
        }

        fun borderRectangleOptions(): Paint {
            val paint = Paint()
            paint.color = Color.BLACK
            paint.style = Paint.Style.STROKE
            return paint
        }

        fun fillRectangleOptions(color: Int): Paint {
            val paint = Paint()
            paint.color = color
            paint.style = Paint.Style.FILL
            return paint
        }

        fun setTextOptions(color: Int, type: Int, size: Float): Paint {
            val paint = Paint()
            paint.color = color
            paint.typeface = Typeface.create(Typeface.DEFAULT, type)
            paint.textSize = size
            return paint
        }

        fun closeDocument() {
            // finish the page
            pdfDocument.finishPage(page)

            // write the document content
            val directoryPath = Environment.getExternalStorageDirectory().path + "/mypdf/"
            val file = File(directoryPath)
            if (!file.exists()) {
                file.mkdirs()
            }
            val targetPdf = directoryPath + "test-2.pdf"
            val filePath = File(targetPdf)
            try {
                Log.d("TITI","done")
                pdfDocument.writeTo(FileOutputStream(filePath))
            } catch (e: IOException) {
                Log.e("main", "error $e")
            }

            // close the document
            pdfDocument.close()
        }
    }


}