// URL de requisicoes
var url = "http://localhost:8080/agenda2/contatos"

setQtdRegistros(0)
listaRegistros()

// Atualiza quantidade de registros
function setQtdRegistros (quantidade) {
    $('#tabelaMembros').data('quantidadeRegistros', quantidade)
    $('#totalMembros').text(quantidade)
}

// Incrementa a quantidade de registros
function incrementaRegistros () {
    var quantidade = parseInt($('#tabelaMembros').data('quantidadeRegistros'), 10) + 1
    setQtdRegistros(quantidade)
    return quantidade
}

// Decrementa a quantidade de registros
function decrementaRegistros () {
    var quantidade = parseInt($('#tabelaMembros').data('quantidadeRegistros'), 10) - 1
    setQtdRegistros(quantidade)
    return quantidade
}

// Formata registro
function formataRegistro (contato) {
    return '<tr data-id="'+contato.id+'">\
                <td class="align-middle" data-campo="nome">'+contato.nome+'</td>\
                <td class="align-middle" data-campo="telefone">'+contato.telefone+'</td>\
                <td class="align-middle" data-campo="email">'+contato.email+'</td>\
                <td class="align-middle" data-campo="endereco">'+contato.endereco+'</td>\
                <td class="align-middle text-center">\
                    <input class="btn btn-outline-primary btn-sm" data-toggle="modal" data-target="#modalFormulario" data-action="editar" type="button" value="Editar">\
                    <input class="btn btn-outline-danger btn-sm remover" type="button" value="Remover">\
                </td>\
            </tr>';
}

// Insere contato na tabela
function insereContato (contato) {
    var tabela = $('#tabelaMembros')
    tabela.find('thead').show()
    var corpoTabela = tabela.find('tbody')
    corpoTabela.find("#agendaVazia").hide()
    corpoTabela.append(formataRegistro(contato))
    incrementaRegistros()
}

// Lista registros
function listaRegistros () {
    $.ajax({
        type: "GET",
        url: url,
        success: function(contatos) {
            $.each(contatos, function(index, contato) {
                insereContato(contato)
            })
        },
        error: function(xhr, status, error) {
            alerta("Erro ao buscar contatos!")
        }
    })
}

// Alert com delay
function alerta (mensagem) {
    setTimeout(function() {
  	alert(mensagem)
    },10)
}

// Limpa espaços em branco
$('#formulario input[type=text], #formulario input#email').on('blur', function(e) {
    $(this).val($.trim($(this).val()))
})

// Escuta evento que é invocado quando o modal acaba de ser oculto ao usuário
$('#modalFormulario').on('show.bs.modal', function (event) {
    var botao = $(event.relatedTarget)  // Botão que acionou o modal
    var acao = botao.data('action')  // Extrai informação do atributo data-action
    var modal = $(this);
    var formulario = modal.find('form')
    var inputId = formulario.find('#id')
    var botaoSubmeter = formulario.find('#submit')
    
    botaoSubmeter.attr('data-action', acao)

    switch (acao) {
        case 'adicionar':
            modal.find('#rotuloModal').text('Adicionar contato')

            inputId.removeAttr('value')
            botaoSubmeter.val('Adicionar')
            break

        case 'editar':
            modal.find('#rotuloModal').text('Editar contato')

            
            var registro = botao.closest('tr')  // Encontra a linha do registro
            inputId.val(registro.data('id'))
            botaoSubmeter.val('Salvar')

            var registro = botao.closest('tr')  // Encontra a linha do registro
            
            var nome = registro.find('td[data-campo=nome]').text()
            var telefone = registro.find('td[data-campo=telefone]').text()
            var email = registro.find('td[data-campo=email]').text()
            var endereco = registro.find('td[data-campo=endereco]').text()

            formulario.find('#nome').val(nome)
            formulario.find('#telefone').val(telefone)
            formulario.find('#email').val(email)
            formulario.find('#endereco').val(endereco)
            break

        default:
            // Se o modal foi aberto por um evento não reconhecido, ele deve ser fechado
            modal.modal('hide')
            break;
    }
})

// Escuta evento que é invocado quando o modal acaba de ser oculto ao usuário
$('#modalFormulario').on('hidden.bs.modal', function (event) {
    $(this).find('#rotuloModal').text('')  // Limpa rótulo do modal
    $(this).find('form')[0].reset()  // Limpa campos do formulário
    $(this).find('form').find("#id").removeAttr('value')  // Remove valor do id
})

// Trata a submissão do formulário
$('#formulario').submit(function(e) {
    // Previne ação padrão do evento de submissão
    e.preventDefault()

    // Organiza os dados do formulario em um objeto
    var data = $(this).serializeArray().reduce(function(obj, item) {
        obj[item.name] = $.trim(item.value)
        
        return obj
    }, {});

    var corpoTabela = $('#tabelaMembros tbody')

    if (data.id==="") {  // Adiciona novo registro
        $.ajax({
            type: "POST",
            url: url,
            data: data,
            success: function(contato) {
                insereContato(contato)
                alerta("Contato adicionado com sucesso!")

                $("#modalFormulario").modal('hide')
            },
            error: function(xhr, status, error) {
                alerta("Erro ao inserir contato, tente novamente!")
            }
        })
    }
    else {  // Edita registro
        $.ajax({
            type: "PUT",
            url: url,
            data: JSON.stringify(data),
            contentType: 'application/json',
            success: function(contato) {
                var registro = corpoTabela.find("tr[data-id="+contato.id+"]")
                registro.find("td[data-campo=nome]").text(contato.nome)
                registro.find("td[data-campo=telefone]").text(contato.telefone)
                registro.find("td[data-campo=email]").text(contato.email)
                registro.find("td[data-campo=endereco]").text(contato.endereco)
                alerta("Contato editado com sucesso!")
                
                $("#modalFormulario").modal('hide')
            },
            error: function(xhr, status, error) {
                alerta("Erro ao inserir contato, tente novamente!")
            }
        })
    }
})

// Trata remoção de registro
$(document).on('click', 'input.remover', function(event) {
    var registro = $(this).closest('tr')
    var contato = {id: registro.data('id').toString()}
    
    // Preenche o objeto com os dados do contato
    registro.children('td[data-campo]').each(function(index, campo) {
        contato[$(campo).data('campo')] = $(campo).text()
    })
    
    $.ajax({
        type: "DELETE",
        url: url,
        data: JSON.stringify(contato),
        contentType: 'application/json',
        success: function(res) {
            if(decrementaRegistros() == 0) {
                var tabela = $('#tabelaMembros')
                tabela.find('thead').hide()
                var corpoTabela = tabela.find('tbody')
                corpoTabela.find("#agendaVazia").show()
            }
            registro.remove()
            alerta("Contato removido com sucesso!")
        },
        error: function(xhr, status, error) {
            alerta("Erro ao remover contato, tente novamente!")
        }
    })
})