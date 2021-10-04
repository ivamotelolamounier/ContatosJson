package com.ivamotelo.contatosCard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

/**
 * Cria-se a função 'getExtras()' para recuperar os dados passados da MainActivity, quando da
 * seleção do contato para ser mostrado em detalhes na Activity 'ContactsDetail'
 */
class ContactsDetails : AppCompatActivity() {
    private var contact : Contacts? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_details)

        initToolBar()
        getExtras()
        bindViews()
    }

    /**
     * Criação da função 'getExtras' para fins de recuperar os dados do contato selecionado para
     * exibir os detalhes, através da chaveValor 'EXTRA_CONTACT'
     */
    fun getExtras(){
        contact = intent.getParcelableExtra(EXTRA_CONTACT)
    }

    /**
     * Estando os dados salvos na variável 'contact', os mesmos serão 'setados' na tela da Activity
     * 'activity_contact_details.xml', fazendo o 'bind' - ligação dos dados entre as telas
     */
    private fun bindViews(){
        findViewById<TextView>(R.id.tv_name).text = contact?.name
        findViewById<TextView>(R.id.tv_phone).text = contact?.phone
    }

    /**
     * Criação de um companation object (Visível em toda aplicação) da chave x valor referente
     * ao contato que será selecionado na Activiy e será recuperada na Activity ContactsDetail
     */
    companion object {
        const val EXTRA_CONTACT : String = "EXTRA_CONTACT"
    }

    /**
     * Implementação de uma 'toolBar' na Activity 'ContactDetails'
     * habilita (true) o botão voltar na toolBar com o método 'supportActionBar, que
     * pode receber nulo (?)
     */
    private fun initToolBar(){
        val toolbar = findViewById<Toolbar>(R.id.menu_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    /**
     * Após o toque na seta para voltar, é implementado o método  nativo 'onSupportNavegateUp()
     * para retornar a tela principal ActivityMain, destuindo a Activity ativa 'activity_contact_detail.xml
     * e reconstruindo a activity principal MainActivity. Pode-se utilizar o modo nativo super.
     * ou simplesmente retornar verdadeiro 'return true'
     */
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
        //return super.onSupportNavigateUp()
    }
}