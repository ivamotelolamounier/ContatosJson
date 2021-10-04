package com.ivamotelo.contatosCard

import android.os.Parcelable
import android.widget.ImageView
import kotlinx.android.parcel.Parcelize

/**
 * Implementar as dependências do 'kotlin-android-extensions' no gradle, e fazer a notação'@'
 * como parcelaize. Com a implementação do 'Parcelable', fica habilitado o tráfego de dados entre
 * as telas - necessário destacar que este plugin foi descontinuado em 2021
 */

@Parcelize
data class Contacts(
    var name : String,
    var phone : String,
    var avatar : String
) : Parcelable
