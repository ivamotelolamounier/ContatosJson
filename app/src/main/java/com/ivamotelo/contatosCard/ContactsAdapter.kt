/**
 * Classe para gerenciar as listas do aplicativo, bem como sua reciclagem
 * para sua implementação, é obrigatório, a implementação de outras classes
 * 1- Classe ADAPTER - função de gerenciar a lista de contatos
 * 2- Classe ViewHolder - responsável por gerenciar CADA ITEM da lista, que poderá ser uma classe
 * externa ou interna (caso em uso).
 * 3- No construtor da classe 'ContactAdapter', é passada a classe 'ContactAdapterViewHolder' como
 * referência.
 * 4- a classe 'ContactAdapter, exige a implementação de alguns métodos OBRIGATÓRIOS:
 * a) - onCreateViewHolder
 * b) - onBindViewHolder
 * c) - getItemCount
 *
 *
 */
package com.ivamotelo.contatosCard

import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * A classe ADAPTER, gerencia toda a lista da Data Classe Contact, e o 'Holder', gerencia
 * cada Iten da Data Classe.
 * Para que a tela de detalhes do contato 'ContactDetails' seja aberta e receba os dados do
 * adapter, é necessário passa-la como parâmentro no construtor 'ContactsAdapter()'
 */
class ContactsAdapter(var listener: ClickItensContactsListener) :
    RecyclerView.Adapter<ContactsAdapter.ContatctsAdapterViewHolder>() {
    /**
     * É necessário a criação de uma MutableList para armazenar os dados que serão exibidos
     * na RecyclerView, que terá a  Data classe 'Contact' (modelo de dado desejado)
     */
    val list : MutableList<Contacts> = mutableListOf()

    /**
     * Esta função 'onCreateViewHolder', é reponsável por criar cada item na tela do app, diferente
     * da função 'onBindViewHolder'. A primeira, é a criação do elemento grafico na RecyclerView,
     * e a segunda é sua 'população' com os dados a serem exibidos. Assim, esta função CRIA O LAYOUT
     * e INFRA a View, anterior a função OnBindViewHolder, que povoa esta criação com os dados a
     * serem exibidos. O retorno será a classe 'ContactAdapterViewHoder, com a a variável 'view'
     * como parâmetro que é um CARD.
     * Para que o contato selecionado seja passado para a tela de detalhes do contato 'ContactDetails'
     * é necessário que o retorno 'ContactsAdapterViewHolder(), também receba em seu construtor, o
     * 'listner'
    */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContatctsAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return ContatctsAdapterViewHolder(view, list, listener)
    }

    /**
     * Função responsável por percorrer a array de itens da lista  e preencher a mesma na tela
     * chamado de 'bind', que consiste em 'popular' os cards da RecyclerView.
     * A lógica da função 'onBindViewHolder', consiste na renderização da RecyclerView, atualizando
     * a posição dos cards a cada rolagem na tela, ou a cada passagem pelo item.
     * Assim, sendo da mesma classe 'ContactAdapterViewHolder', implementa-se o 'holder.bind'
     * que recebe 'list', através do index 'position' do construtor
     */
    override fun onBindViewHolder(holder: ContatctsAdapterViewHolder, position: Int) {
        holder.bind(list[position])
    }

    /**
     * Função para informar quantos itens possui a lista, que será retornada como Int.
     */
    override fun getItemCount(): Int {
        return list.size
    }

    /**
     * Para passar a lista de uma classe EXTERNA (MainActivity.kt) para dentro do ADAPTER,
     * é necessaŕio a criaçao de uma funçao PÚBLICA que acessará o adapter e repasse a lista
     * para a classe 'ContactsAdapter'
     * 1- o método recebe uma lista do tipo list
     * 2- sempre que o método for chamado, ele limpará a lista interna (list)
     * 3- após a limpeza da lista, a mesma será populada novamente
     * 4- isso feito, será chamdo o método 'notifyDataSetChanged ' que notificará o adapter,
     * informando que a lista (list) utilizada para fazer a renderização, foi modificada.
     * Deste modo, reinicia-se o ciclo da lógica implementada.
     */
    fun updateLista(list: List<Contacts>){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    /**
     * A classe 'ContactAdapter', gerencia TODA a lista da Data Classe 'Contact' e a classe
     * ContactAdapterViewHolder', gerencia CADA ITEM da lista Data Classe, ou seja, cada
     * "contato" de Contact.
     * São declaradas as val referentes aos campos da lista, que serão ligados á RecyclerView
     * através de seus 'id' do arquivo 'contact_item.xml'.
     * Isso feito, na função 'bind()', será atribuido a cada item da 'fun getItemCount()' para
     * cada componente Text e Image da ViewCard.
     * Para que o contato selecionado seja exibido na tela de 'ContactDetails', é necessário passar
     * uma var 'list', do tipo List<Contacts>, dentro do construtor da classe ContactsAdapterViewHolder.
     * Do mesmo modo, é necessário a passagem no construtor da mesma classe, do método 'listner' do tipo
     * ClickItensContactsListener.
     */
    class ContatctsAdapterViewHolder(ItemView : View, var list: List<Contacts>, listener: ClickItensContactsListener) :
        RecyclerView.ViewHolder(ItemView) {
        private val iv_photo: ImageView = ItemView.findViewById(R.id.iv_photo)
        private val tv_name: TextView = ItemView.findViewById(R.id.tv_name)
        private val tv_phone: TextView = ItemView.findViewById(R.id.tv_phone)

        /**
         * Cria o click dentro do item, ou seja, quando o usuário clicar no item da lista selecionado
         * o método 'setOnClickListener' do listener será chamado, no caso, em cima da View 'itemView'
         * que chamará o método 'listener.clickItenContact' da interface 'ClickItensContactsListener'
         * que foi implementado na Activity, que por sua vez irá executar a instrução declarada na
         * 'init' abaixo
         */
        init {
            itemView.setOnClickListener{
                listener.clickItenContact(list[adapterPosition])
        }
    }

        fun bind(contact: Contacts){
            tv_name.text = contact.name
            tv_phone.text = contact.phone
            // falta tratar iv_photo

        }
    }
}