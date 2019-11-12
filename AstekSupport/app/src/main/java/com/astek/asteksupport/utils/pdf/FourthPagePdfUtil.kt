package com.astek.asteksupport.utils.pdf

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.text.TextPaint
import android.util.Log
import com.astek.asteksupport.R
import com.astek.asteksupport.utils.AuthenticationUtil.Companion.employeeDocumentId
import com.astek.asteksupport.utils.pdf.FifthPagePdfUtil.Companion.createFifthPage
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.borderRectangleOptions
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.drawText
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.drawTextWithoutJustification
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.fillRectangleOptions
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.footer
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.setTextOptions
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.setUnderlineTextOptions
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.writeInterviewPlan
import com.google.firebase.firestore.FirebaseFirestore


@Suppress("DEPRECATION")
class FourthPagePdfUtil {

    companion object {

        private lateinit var canvas: Canvas
        private lateinit var textPaint: TextPaint
        private lateinit var pdfDocument: PdfDocument
        private lateinit var page: PdfDocument.Page

        private const val TAG = "ThirdPdfPage"

        @SuppressLint("StaticFieldLeak")
        private val db = FirebaseFirestore.getInstance()

        @SuppressLint("StaticFieldLeak")
        private lateinit var activity: Activity


        fun createFourthPage(canvasValue: Canvas, textPaintValue: TextPaint, activityValue: Activity, pdfDocumentValue: PdfDocument, pageValue: PdfDocument.Page) {

            canvas = canvasValue
            textPaint = textPaintValue
            activity = activityValue
            pdfDocument = pdfDocumentValue
            page = pageValue

            footer("4")

            writeInterviewPlan(1)

            val firstTitle = activity.getString(R.string.pdfSkillEvaluation)
            textPaint.set(setUnderlineTextOptions(Color.BLACK, Typeface.BOLD, 14F))
            drawText(firstTitle, 555, 20F, 135F, 1.0F, false)

            //First Rectangle
            val skillText = activity.getString(R.string.pdfSkill)
            var rect = Rect(60, 160, 178, 215)
            canvas.drawRect(rect, fillRectangleOptions(activity.getColor(R.color.yellow)))
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setUnderlineTextOptions(activity.getColor(R.color.blue), Typeface.NORMAL, 10F))
            drawText(skillText,118,60F,180F,1.0F,true)

            //Second Rectangle
            rect = Rect(178, 160, 208, 215)
            canvas.drawRect(rect, fillRectangleOptions(activity.getColor(R.color.yellow)))
            canvas.drawRect(rect, borderRectangleOptions())
            canvas.rotate(270F)
            textPaint.set(setUnderlineTextOptions(activity.getColor(R.color.blue), Typeface.NORMAL, 8F))
            drawText(activity.getString(R.string.employeeGraduation),45,-211F,182F,1.0F,true)

            //Third Rectangle
            canvas.rotate(90F)
            rect = Rect(208, 160, 238, 215)
            canvas.drawRect(rect, fillRectangleOptions(activity.getColor(R.color.yellow)))
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setUnderlineTextOptions(activity.getColor(R.color.red), Typeface.NORMAL, 9F))
            canvas.rotate(270F)
            drawText(activity.getString(R.string.managerGraduation),45,-212F,211F,1.0F,true)

            //Fourth Rectangle
            canvas.rotate(90F)
            rect = Rect(238, 160, 535, 215)
            canvas.drawRect(rect, fillRectangleOptions(activity.getColor(R.color.yellow)))
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setUnderlineTextOptions(activity.getColor(R.color.blue), Typeface.NORMAL, 10F))
            drawText(activity.getString(R.string.skillExample),297,238F,172F,1.0F,true)
            textPaint.set(setUnderlineTextOptions(activity.getColor(R.color.red), Typeface.NORMAL, 10F))
            drawText(activity.getString(R.string.improvementAndGain),297,238F,188F,1.0F,true)

            //TechnicalSkill
            val technicalText = activity.getString(R.string.pdfTechnical)
            rect = Rect(60, 215, 535, 230)
            canvas.drawRect(rect, fillRectangleOptions(activity.getColor(R.color.green)))
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.BOLD, 9F))
            drawText(technicalText,465,65F,215F,1.0F,false)
            retrieveTechnicalValue()
        }




        /************************************* Technical *******************************************/
        private fun writeFirstTechnicalSkill(value: String) {
            val rect = Rect(60, 230, 178, 245)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 9F))
            drawText(value,118,60F,231F,1.0F,true)
        }

        private fun writeSecondTechnicalSkill(value: String) {
            val rect = Rect(60, 245, 178, 260)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 9F))
            drawText(value,118,60F,246F,1.0F,true)
        }


        private fun writeFirstTechnicalEmployeeGraduation(value: String) {
            val rect = Rect(178, 230, 208, 245)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 9F))
            drawText(value,30,178F,231F,1.0F,true)
        }

        private fun writeSecondTechnicalEmployeeGraduation(value: String) {
            val rect = Rect(178, 245, 208, 260)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 9F))
            drawText(value,30,178F,246F,1.0F,true)
        }


        private fun writeFirstTechnicalManagerGraduation(value: String) {
            val rect = Rect(208, 230, 238, 245)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 9F))
            drawText(value,30,208F,231F,1.0F,true)
        }

        private fun writeSecondTechnicalManagerGraduation(value: String) {
            val rect = Rect(208, 245, 238, 260)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 9F))
            drawText(value,30,208F,246F,1.0F,true)
        }


        private fun writeFirstTechnicalExample(value: String) {
            val rect = Rect(238, 230, 535, 245)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 9F))
            drawText(value,297,238F,231F,1.0F,true)
        }

        private fun writeSecondTechnicalExample(value: String) {
            var rect = Rect(238, 245, 535, 260)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 9F))
            drawText(value,297,238F,246F,1.0F,true)


            //ProfessionalSkill
            val professionalText = activity.getString(R.string.pdfProfession)
            rect = Rect(60, 260, 535, 275)
            canvas.drawRect(rect, fillRectangleOptions(activity.getColor(R.color.green)))
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.BOLD, 10F))
            drawText(professionalText,465,65F,260F,1.0F,false)
            retrieveProfessionalValue()
        }




        /************************************* Professional *******************************************/
        private fun writeFirstProfessionalSkill(value: String) {
            val rect = Rect(60, 275, 178, 290)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 9F))
            drawText(value,118,60F,276F,1.0F,true)
        }

        private fun writeSecondProfessionalSkill(value: String) {
            val rect = Rect(60, 290, 178, 305)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 9F))
            drawText(value,118,60F,291F,1.0F,true)
        }

        private fun writeThirdProfessionalSkill(value: String) {
            val rect = Rect(60, 305, 178, 320)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 9F))
            drawText(value,118,60F,306F,1.0F,true)
        }


        private fun writeFirstProfessionalEmployeeGraduation(value: String) {
            val rect = Rect(178, 275, 208, 290)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 9F))
            drawText(value,30,178F,276F,1.0F,true)
        }

        private fun writeSecondProfessionalEmployeeGraduation(value: String) {
            val rect = Rect(178, 290, 208, 305)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 9F))
            drawText(value,30,178F,291F,1.0F,true)
        }

        private fun writeThirdProfessionalEmployeeGraduation(value: String) {
            val rect = Rect(178, 305, 208, 320)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 9F))
            drawText(value,30,178F,306F,1.0F,true)
        }


        private fun writeFirstProfessionalManagerGraduation(value: String) {
            val rect = Rect(208, 275, 238, 290)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 9F))
            drawText(value,30,208F,276F,1.0F,true)
        }

        private fun writeSecondProfessionalManagerGraduation(value: String) {
            val rect = Rect(208, 290, 238, 305)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 9F))
            drawText(value,30,208F,291F,1.0F,true)
        }

        private fun writeThirdProfessionalManagerGraduation(value: String) {
            val rect = Rect(208, 305, 238, 320)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 9F))
            drawText(value,30,208F,306F,1.0F,true)
        }


        private fun writeFirstProfessionalExample(value: String) {
            val rect = Rect(238, 275, 535, 290)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 9F))
            drawText(value,297,238F,276F,1.0F,true)
        }

        private fun writeSecondProfessionalExample(value: String) {
            val rect = Rect(238, 290, 535, 305)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 9F))
            drawText(value,297,238F,291F,1.0F,true)
        }

        private fun writeThirdProfessionalExample(value: String) {
            var rect = Rect(238, 305, 535, 320)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 9F))
            drawText(value,297,238F,306F,1.0F,true)

            //FunctionalSkill
            val functionalText = activity.getString(R.string.pdfFunctional)
            rect = Rect(60, 320, 535, 335)
            canvas.drawRect(rect, fillRectangleOptions(activity.getColor(R.color.green)))
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.BOLD, 10F))
            drawText(functionalText,465,65F,320F,1.0F,false)
            retrieveFunctionalValue()
        }


        /************************************* Functional *******************************************/
        private fun writeFirstFunctionalSkill(value: String) {
            val rect = Rect(60, 335, 178, 350)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 9F))
            drawText(value,118,60F,336F,1.0F,true)
        }

        private fun writeSecondFunctionalSkill(value: String) {
            val rect = Rect(60, 350, 178, 365)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 9F))
            drawText(value,118,60F,351F,1.0F,true)
        }


        private fun writeFirstFunctionalEmployeeGraduation(value: String) {
            val rect = Rect(178, 335, 208, 350)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 9F))
            drawText(value,30,178F,336F,1.0F,true)
        }

        private fun writeSecondFunctionalEmployeeGraduation(value: String) {
            val rect = Rect(178, 350, 208, 365)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 9F))
            drawText(value,30,178F,351F,1.0F,true)
        }


        private fun writeFirstFunctionalManagerGraduation(value: String) {
            val rect = Rect(208, 335, 238, 350)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 9F))
            drawText(value,30,208F,336F,1.0F,true)
        }

        private fun writeSecondFunctionalManagerGraduation(value: String) {
            val rect = Rect(208, 350, 238, 365)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 9F))
            drawText(value,30,208F,351F,1.0F,true)
        }


        private fun writeFirstFunctionalExample(value: String) {
            val rect = Rect(238, 335, 535, 350)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 9F))
            drawText(value,297,238F,336F,1.0F,true)
        }

        private fun writeSecondFunctionalExample(value: String) {
            var rect = Rect(238, 350, 535, 365)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 9F))
            drawText(value,297,238F,351F,1.0F,true)

            //ManagerialSkill
            val managerialText = activity.getString(R.string.pdfManagerial)
            rect = Rect(60, 365, 535, 380)
            canvas.drawRect(rect, fillRectangleOptions(activity.getColor(R.color.green)))
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.BOLD, 10F))
            drawText(managerialText,465,65F,365F,1.0F,false)
            retrieveManagerialValue()
        }





        /************************************* Managerial *******************************************/
        private fun writeFirstManagerialSkill(value: String) {
            val rect = Rect(60, 380, 178, 395)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 9F))
            drawText(value,118,60F,381F,1.0F,true)
        }

        private fun writeSecondManagerialSkill(value: String) {
            val rect = Rect(60, 395, 178, 410)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 9F))
            drawText(value,118,60F,395F,1.0F,true)
        }


        private fun writeFirstManagerialEmployeeGraduation(value: String) {
            val rect = Rect(178, 380, 208, 395)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 9F))
            drawText(value,30,178F,381F,1.0F,true)
        }

        private fun writeSecondManagerialEmployeeGraduation(value: String) {
            val rect = Rect(178, 395, 208, 410)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 9F))
            drawText(value,30,178F,395F,1.0F,true)
        }


        private fun writeFirstManagerialManagerGraduation(value: String) {
            val rect = Rect(208, 380, 238, 395)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 9F))
            drawText(value,30,208F,381F,1.0F,true)
        }

        private fun writeSecondManagerialManagerGraduation(value: String) {
            val rect = Rect(208, 395, 238, 410)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 9F))
            drawText(value,30,208F,395F,1.0F,true)
        }


        private fun writeFirstManagerialExample(value: String) {
            val rect = Rect(238, 380, 535, 395)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 9F))
            drawText(value,297,238F,381F,1.0F,true)
        }

        private fun writeSecondManagerialExample(value: String) {
            var rect = Rect(238, 395, 535, 410)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 9F))
            drawText(value,297,238F,395F,1.0F,true)

            //BehaviouralSkill
            val behaviouralText = activity.getString(R.string.pdfBehavioural)
            rect = Rect(60, 410, 535, 425)
            canvas.drawRect(rect, fillRectangleOptions(activity.getColor(R.color.green)))
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.BOLD, 10F))
            drawText(behaviouralText,465,65F,410F,1.0F,false)

            retrieveValue("autonomySkillEvaluation",activity.getString(R.string.skillAutonomy),425,440,426F)
        }

        /************************************* Behavioural *******************************************/
        private fun writeSkillTitle(skillTitle: String , top: Int , bottom: Int , y: Float) {
            val rect = Rect(60, top, 178, bottom)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 6F))
            drawTextWithoutJustification(skillTitle,116,63F,y,0.9F,false)
        }

        private fun writeEmployeeGraduation(value: String , top: Int , bottom: Int , y: Float) {
            val rect = Rect(178, top, 208, bottom)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 9F))
            drawTextWithoutJustification(value,30,178F, y,1.0F,true)
        }

        private fun writeManagerGraduation(value: String , top: Int , bottom: Int , y: Float) {
            val rect = Rect(208, top, 238, bottom)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 9F))
            drawTextWithoutJustification(value,30,208F,y,1.0F,true)
        }

        private fun writeExample(value: String , top: Int , bottom: Int , y: Float) {
            val rect = Rect(238, top, 535, bottom)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 9F))
            drawTextWithoutJustification(value,297,238F,y,1.0F,true)
        }


        private fun writeSkillEvaluation() {

            //Title
            var rect = Rect(60, 592, 535, 607)
            canvas.drawRect(rect, fillRectangleOptions(activity.getColor(R.color.green)))
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setUnderlineTextOptions(activity.getColor(R.color.red), Typeface.BOLD, 10F))
            drawText(activity.getString(R.string.titleSkillEvaluation), 435, 80F, 592F, 1.0F, true)

            //First Rectangle
            rect = Rect(60, 607, 179, 625)
            canvas.drawRect(rect, fillRectangleOptions(activity.getColor(R.color.yellow)))
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 9F))
            drawText(activity.getString(R.string.expertise),119,60F,610F,1.0F,true)

            //Second Rectangle
            rect = Rect(179, 607, 298, 625)
            canvas.drawRect(rect, fillRectangleOptions(activity.getColor(R.color.yellow)))
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 9F))
            drawText(activity.getString(R.string.autonomousCapacity),119,179F,610F,1.0F,true)

            //Third Rectangle
            rect = Rect(298, 607, 417, 625)
            canvas.drawRect(rect, fillRectangleOptions(activity.getColor(R.color.yellow)))
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 9F))
            drawText(activity.getString(R.string.partialCapacity),119,298F,610F,1.0F,true)

            //Fourth Rectangle
            rect = Rect(417, 607, 535, 625)
            canvas.drawRect(rect, fillRectangleOptions(activity.getColor(R.color.yellow)))
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 9F))
            drawText(activity.getString(R.string.notion),119,417F,610F,1.0F,true)

            retrieveManagerEvaluationValue()
        }


        private fun fillRectangleValue(text: String) {
            val firstRect = Rect(60, 625, 179, 645)
            canvas.drawRect(firstRect, borderRectangleOptions())
            val secondRect = Rect(179, 625, 298, 645)
            canvas.drawRect(secondRect, borderRectangleOptions())
            val thirdRect = Rect(298, 625, 417, 645)
            canvas.drawRect(thirdRect, borderRectangleOptions())
            val fourthRect = Rect(417, 625, 535, 645)
            canvas.drawRect(fourthRect, borderRectangleOptions())

            when(text) {
                "Expertise" -> canvas.drawRect(firstRect, fillRectangleOptions(Color.LTGRAY))
                "Capacité Autonome" -> canvas.drawRect(secondRect, fillRectangleOptions(Color.LTGRAY))
                "Capacité Partielle" -> canvas.drawRect(thirdRect, fillRectangleOptions(Color.LTGRAY))
                "Notion" -> canvas.drawRect(fourthRect, fillRectangleOptions(Color.LTGRAY))
            }

        }

        /************************************* DataBase *******************************************/
        private fun retrieveTechnicalValue() {
            db.collection("users").document(employeeDocumentId)
                .collection("technicalSkillEvaluation")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        writeFirstTechnicalSkill(document.get("skill1").toString())
                        if (document.get("skill2") != null) {
                            writeSecondTechnicalSkill(document.get("skill2").toString())
                        } else {
                            writeSecondTechnicalSkill("/")
                        }

                        writeFirstTechnicalEmployeeGraduation(document.get("employeeGraduation1").toString())
                        if (document.get("employeeGraduation2") != null) {
                            writeSecondTechnicalEmployeeGraduation(document.get("employeeGraduation2").toString())
                        } else {
                            writeSecondTechnicalEmployeeGraduation("/")
                        }

                        writeFirstTechnicalManagerGraduation(document.get("managerGraduation1").toString())
                        if (document.get("managerGraduation2") != null) {
                            writeSecondTechnicalManagerGraduation(document.get("managerGraduation2").toString())
                        } else {
                            writeSecondTechnicalManagerGraduation("/")
                        }

                        val value1 = document.get("skillExample1").toString() + " / " + document.get("improvementAndGain1").toString()
                        writeFirstTechnicalExample(value1)
                        if (document.get("skillExample2") != null) {
                            val value2 = document.get("skillExample2").toString() + " / " + document.get("improvementAndGain2").toString()
                            writeSecondTechnicalExample(value2)
                        } else {
                            writeSecondTechnicalExample("/")
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents.", exception)
                }        }


        private fun retrieveProfessionalValue() {
            db.collection("users").document(employeeDocumentId)
                .collection("professionSkillEvaluation")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        writeFirstProfessionalSkill(document.get("skill1").toString())
                        if (document.get("skill2") != null) {
                            writeSecondProfessionalSkill(document.get("skill2").toString())
                        } else {
                            writeSecondProfessionalSkill("/")
                        }
                        if (document.get("skill3") != null) {
                            writeThirdProfessionalSkill(document.get("skill3").toString())
                        } else {
                            writeThirdProfessionalSkill("/")
                        }

                        writeFirstProfessionalEmployeeGraduation(document.get("employeeGraduation1").toString())
                        if (document.get("employeeGraduation2") != null) {
                            writeSecondProfessionalEmployeeGraduation(document.get("employeeGraduation2").toString())
                        } else {
                            writeSecondProfessionalEmployeeGraduation("/")
                        }
                        if (document.get("employeeGraduation3") != null) {
                            writeThirdProfessionalEmployeeGraduation(document.get("employeeGraduation3").toString())
                        } else {
                            writeThirdProfessionalEmployeeGraduation("/")
                        }

                        writeFirstProfessionalManagerGraduation(document.get("managerGraduation1").toString())
                        if (document.get("managerGraduation2") != null) {
                            writeSecondProfessionalManagerGraduation(document.get("managerGraduation2").toString())
                        } else {
                            writeSecondProfessionalManagerGraduation("/")
                        }
                        if (document.get("managerGraduation3") != null) {
                            writeThirdProfessionalManagerGraduation(document.get("managerGraduation3").toString())
                        } else {
                            writeThirdProfessionalManagerGraduation("/")
                        }

                        val value1 = document.get("skillExample1").toString() + " / " + document.get("improvementAndGain1").toString()
                        writeFirstProfessionalExample(value1)
                        if (document.get("skillExample2") != null) {
                            val value2 = document.get("skillExample2").toString() + " / " + document.get("improvementAndGain2").toString()
                            writeSecondProfessionalExample(value2)
                        } else {
                            writeSecondProfessionalExample("/")
                        }
                        if (document.get("skillExample3") != null) {
                            val value3 = document.get("skillExample3").toString() + " / " + document.get("improvementAndGain3").toString()
                            writeThirdProfessionalExample(value3)
                        } else {
                            writeThirdProfessionalExample("/")
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents.", exception)
                }
        }

        private fun retrieveFunctionalValue() {
            db.collection("users").document(employeeDocumentId)
                .collection("functionalSkillEvaluation")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        writeFirstFunctionalSkill(document.get("skill1").toString())
                        if (document.get("skill2") != null) {
                            writeSecondFunctionalSkill(document.get("skill2").toString())
                        } else {
                            writeSecondFunctionalSkill("/")
                        }

                        writeFirstFunctionalEmployeeGraduation(document.get("employeeGraduation1").toString())
                        if (document.get("employeeGraduation2") != null) {
                            writeSecondFunctionalEmployeeGraduation(document.get("employeeGraduation2").toString())
                        } else {
                            writeSecondFunctionalEmployeeGraduation("/")
                        }

                        writeFirstFunctionalManagerGraduation(document.get("managerGraduation1").toString())
                        if (document.get("managerGraduation2") != null) {
                            writeSecondFunctionalManagerGraduation(document.get("managerGraduation2").toString())
                        } else {
                            writeSecondFunctionalManagerGraduation("/")
                        }

                        val value1 = document.get("skillExample1").toString() + " / " + document.get("improvementAndGain1").toString()
                        writeFirstFunctionalExample(value1)
                        if (document.get("skillExample2") != null) {
                            val value2 = document.get("skillExample2").toString() + " / " + document.get("improvementAndGain2").toString()
                            writeSecondFunctionalExample(value2)
                        } else {
                            writeSecondFunctionalExample("/")
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents.", exception)
                }
        }


        private fun retrieveManagerialValue() {
            db.collection("users").document(employeeDocumentId)
                .collection("managerialSkillEvaluation")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        writeFirstManagerialSkill(document.get("skill1").toString())
                        if (document.get("skill2") != null) {
                            writeSecondManagerialSkill(document.get("skill2").toString())
                        } else {
                            writeSecondManagerialSkill("/")
                        }

                        writeFirstManagerialEmployeeGraduation(document.get("employeeGraduation1").toString())
                        if (document.get("employeeGraduation2") != null) {
                            writeSecondManagerialEmployeeGraduation(document.get("employeeGraduation2").toString())
                        } else {
                            writeSecondManagerialEmployeeGraduation("/")
                        }

                        writeFirstManagerialManagerGraduation(document.get("managerGraduation1").toString())
                        if (document.get("managerGraduation2") != null) {
                            writeSecondManagerialManagerGraduation(document.get("managerGraduation2").toString())
                        } else {
                            writeSecondManagerialManagerGraduation("/")
                        }

                        val value1 = document.get("skillExample1").toString() + " / " + document.get("improvementAndGain1").toString()
                        writeFirstManagerialExample(value1)
                        if (document.get("skillExample2") != null) {
                            val value2 = document.get("skillExample2").toString() + " / " + document.get("improvementAndGain2").toString()
                            writeSecondManagerialExample(value2)
                        } else {
                            writeSecondManagerialExample("/")
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents.", exception)
                }
        }

        private fun retrieveValue(collectionName: String,
                                  skillTitle: String,
                                  top: Int,
                                  bottom: Int,
                                  y: Float) {
            db.collection("users").document(employeeDocumentId)
                .collection(collectionName)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        writeSkillTitle(skillTitle, top, bottom, y)
                        val employeeGraduation = document.get("employeeGraduation1").toString()
                        writeEmployeeGraduation(employeeGraduation, top, bottom, y)
                        val managerGraduation = document.get("managerGraduation1").toString()
                        writeManagerGraduation(managerGraduation, top, bottom,  y)
                        val value1 = document.get("skillExample1").toString() + " / " + document.get("improvementAndGain1").toString()
                        writeExample(value1, top, bottom, y)

                        when(skillTitle) {
                            activity.getString(R.string.skillAutonomy) ->
                                retrieveValue("adaptabilitySkillEvaluation",activity.getString(R.string.skillAdaptability),
                                    440,457,442F)
                            activity.getString(R.string.skillAdaptability) ->
                                retrieveValue("teamWorkSkillEvaluation",activity.getString(R.string.skillTeamWork),
                                    457,472,459F)
                            activity.getString(R.string.skillTeamWork) ->
                                retrieveValue("creativitySkillEvaluation",activity.getString(R.string.skillCreativity),
                                    472,487,472F)
                            activity.getString(R.string.skillCreativity) ->
                                retrieveValue("communicationSkillEvaluation",activity.getString(R.string.skillCommunication),
                                    487,502,487F)
                            activity.getString(R.string.skillCommunication) ->
                                retrieveValue("implicationSkillEvaluation",activity.getString(R.string.skillImplication),
                                    502,517,504F)
                            activity.getString(R.string.skillImplication) ->
                                retrieveValue("respectSkillEvaluation",activity.getString(R.string.skillRespect),
                                    517,532,519F)
                            activity.getString(R.string.skillRespect) ->
                                retrieveValue("rigourSkillEvaluation",activity.getString(R.string.skillRigour),
                                    532,547,532F)
                            activity.getString(R.string.skillRigour) -> {

                                //LinguisticSkill
                                val linguisticText = activity.getString(R.string.pdfLinguistic)
                                val rect = Rect(60, 547, 535, 562)
                                canvas.drawRect(rect, fillRectangleOptions(activity.getColor(R.color.green)))
                                canvas.drawRect(rect, borderRectangleOptions())
                                textPaint.set(setTextOptions(Color.BLACK, Typeface.BOLD, 10F))
                                drawText(linguisticText,465,65F,547F,1.0F,false)

                                retrieveValue("englishSkillEvaluation",activity.getString(R.string.skillEnglish),
                                    562,577,564F)
                            }
                            activity.getString(R.string.skillEnglish) ->
                                retrieveValue("othersSkillEvaluation",activity.getString(R.string.skillOther),
                                    577,592,579F)
                            activity.getString(R.string.skillOther) -> {
                                writeSkillEvaluation()
                            }

                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents.", exception)
                }
        }


        private fun retrieveManagerEvaluationValue() {
            db.collection("users").document(employeeDocumentId)
                .collection("skillEvaluation")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        fillRectangleValue(document.get("skillEvaluation").toString())

                        // finish the page
                        pdfDocument.finishPage(page)

                        PdfUtil.createPage(5)

                        //Start Fourth Page
                        createFifthPage(PdfUtil.getCanvas(), PdfUtil.getTextPaint(), activity, PdfUtil.getPdfDocument(), PdfUtil.getPage())
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents.", exception)
                }
        }




    }
}