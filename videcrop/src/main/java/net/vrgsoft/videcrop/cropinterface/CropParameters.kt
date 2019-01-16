package net.vrgsoft.videcrop.cropinterface

import android.graphics.Rect

data class CropParameters(val cropRect: Rect,
                          val startCrop : Long,
                          val cropDuration : Long,
                          val inputPath:String,
                          val outputPath:String)