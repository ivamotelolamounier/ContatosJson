package com.ivamotelo.contatosCard

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.edit
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ivamotelo.contatosCard.ContactsDetails.Companion.EXTRA_CONTACT


class MainActivity : AppCompatActivity(), ClickItensContactsListener {
    /**
     * Declaração de uma variável 'rv_list' da classe RecyclerView, com atraso (lazy)
     * Declaração de uma variável 'adapter' do tipo Adapter, que receberá a classe
     * 'ContactsAdapter()', já instânciada.
     */
    private val rv_list : RecyclerView by lazy {
        findViewById<RecyclerView>(R.id.rv_list)
    }

    /**
     * Aqui, é implementado no construtor do adapter, a chamada para a abertura e passagem de dados
     * necessários a abertura da tela de detelahes do contato 'ContactsDetails', no caso 'this', ou seja
     * a classe atual já implementa a interface
     */
    private val adapter = ContactsAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_menu)

        // A ordem de chamada é importante
        iniDrawer()
        fetchListContacts()
        bindView()

    }

    /**
     * Função utilizada para fazer a chamda da RecyclerView 'rv_list', juntamente com o seu adapter
     * 'adpter' instanciado com a ContactsAdapter().
     * Logo, o adapter da classe 'ContactsAdapter', será utilizado na MainActivity, através da ligaçõa
     * feita com a declaração da val adapter acima.
     * Também é definida a forma como a recyclerView irá se comportar, sendo um tratamento interno, mas
     * necessário para seu funcionamento, através de seus 'layoutManger', que no caso em tela será
     * através do método 'LinearLayoutManeger', no contexto da própria MainActivity (this)
     */
    private fun bindView(){
    rv_list.adapter = adapter
        rv_list.layoutManager = LinearLayoutManager(this)
        updateList()
    }

    /**
     * Método para obter a lista de contatos do arquivo de preferencias do usuário "PREFERENCES"
     * em forma de uma String, necessita de uma chave: 'contatcts' e um valor valor padrão, caso
     * a chave não exista, ou seja nula, retornando um array vazio '[]' para não 'quebrar a aplicação
     * Se a String for válida, então será feita a conversão da String em um objeto de classe através
     * do JSON
     */
    private fun getListContacts() : List<Contacts> {
    val list = getInstanceSharedPreferences().getString("contacts", "[]")
        val turnsType = object : TypeToken<List<Contacts>>(){}.type
        return Gson().fromJson(list, turnsType)
    }

    /**
     * Função que atualiza a lista JSON retornada da função 'getListContacts()
     */
    private fun updateList(){
        val list = getListContacts()
        adapter.updateLista(list)
        //adapter.updateLista(getListContacts())
    }

    /**
     * Método para a criação do MENUS DE CONTEXTO do App
     * utiliza-se o método 'infrate' para infrar o menu, que receberá o xml do arquivo 'menu'
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val infrater : MenuInflater = menuInflater
        infrater.inflate(R.menu.menu, menu)
        return true
    }
    /**
     * Método encapsulado para tratar os TOASTS do componente de menus
     */
    private fun showToast(message : String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()

    }
    /**
     * Método utilizado para receber a opção de menu que foi clicada
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.itens_menu1 -> {
                showToast("Exibindo menu itens 1")
                return true
            }
            R.id.itens_menu2 -> {
                showToast("Exibindo menu itens 2")
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    /**
     * Método para inicializar o MENU TOOLBAR.
     */
    private fun iniDrawer(){
        val drawerLayout = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        val toolbar = findViewById<Toolbar>(R.id.menu_toolbar)
        setSupportActionBar(toolbar)

        val toogle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open_drawer,
            R.string.close_drawer)

        drawerLayout.addDrawerListener(toogle)
        // sincroniza o evento de abrir e fechar com o drawerlayout
        toogle.syncState()

    }

    /**
     * Função para iniciar a atividade referente a tela de detalhes do contato 'ContactsDetails'
     * para que haja o trafego de forma correta, foi necessário o plugin kotlin-extensions, que foi
     * descontinuado.
     * continuando, implementa-se uma 'Intent' com o método 'putextra()' que possui o conceito
     * 'chave x valor' para ser recuperado na tela de detalhes 'ContactDetail', com a mesma 'chaveValor'
     * Esta chaveValor será criada na Activity "ContactDetails'
     * Chave: 'EXTRA_CONTACT' e valor 'contact'
     */
    override fun clickItenContact(contact: Contacts){
        val intent = Intent(this, ContactsDetails::class.java)
        intent.putExtra(EXTRA_CONTACT, contact)
        startActivity(intent)
    }

    /**
     * \método para gravar as preferências do usuário, no caso uma lista de contatos preferidos
     * através da API. O método faz uma consulta na lista e retorna os registros marcados como
     * favoritos
     */
    fun fetchListContacts(){

        val list = arrayListOf(
            Contacts(
                name = "Ivam Otelo",
                phone = "(35) 99999-12345",
                avatar = "img.png"
            ),
            Contacts(
                name = "Elimar da Silva",
                phone = "(32) 45687-3251",
                avatar = "img.png"
            )
        )
        /**
         * Será realizada uma conversão de objeto de classe (array) para uma String, através da
         * biblioteca JSON. Existem dois métodos para gravar a presitência de dados: 'apply' e 'commit'
         * sendo que 'apply' grava de modo sincromo, ou seja, não broqueia a thread principal, já o modo
         * 'commit' e realizado de modo assincorno, o que poderá travar a thread principal, se a
         * gravação da presistência de dados for muito grande. no nosso exemplo, a gravção é
         * praticamente instantânea, mas por segurança, utiliza-se o COMMIT para que a thread seja
         * bloqueada até o final da gravção da presitência dos dados gravados, garantido que seja
         * anterior a uma consulta no app
         */
        getInstanceSharedPreferences().edit {
            val json = Gson().toJson(list)
            putString("contatcts", json)
            commit()
            //putString("contacts", Gson().toJson(list))
        }
    }

    /**
     * Método nativo 'getSharedPreferences()', que tem como contexto conforme documentação do Android
     * que recomenda que o arquivo de preferências seja único no aplicativo e tenha um nome expressivo
     * usando o 'nome do pacote da aplicação", no caso 'com.ivamotelo.contatos. PREFERENCES'
     */
    fun getInstanceSharedPreferences() : SharedPreferences {
        return getSharedPreferences("com.ivamotelo.contatosCard.PREFERENCES", Context.MODE_PRIVATE)
    }
}
