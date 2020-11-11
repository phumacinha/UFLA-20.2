// Inicializa contagem de registros
setQtdRegistros(2)

// Atualiza quantidade de registros
function setQtdRegistros (quantidade) {
    $('#tabelaMembros').data('quantidadeRegistros', quantidade)
    $('#totalMembros').text(quantidade)
}

// Incrementa a quantidade de registros
function incrementaRegistros () {
    var quantidade = parseInt($('#tabelaMembros').data('quantidadeRegistros'), 10) + 1
    setQtdRegistros(quantidade)
}

// Decrementa a quantidade de registros
function decrementaRegistros () {
    var quantidade = parseInt($('#tabelaMembros').data('quantidadeRegistros'), 10) - 1
    setQtdRegistros(quantidade)
}

// Formata registro
function formataRegistro (data) {
    return '<tr>\
                <td class="align-middle" data-campo="nome">'+data.nome+'</td>\
                <td class="align-middle" data-campo="cargo">'+data.cargo+'</td>\
                <td class="align-middle" data-campo="email">'+data.email+'</td>\
                <td class="align-middle" data-campo="admissao">'+data.admissao+'</td>\
                <td class="align-middle text-center">\
                    <input class="btn btn-outline-primary btn-sm" data-toggle="modal" data-target="#modalFormulario" data-action="editar" type="button" value="Editar">\
                    <input class="btn btn-outline-danger btn-sm remover" type="button" value="Remover">\
                </td>\
            </tr>';
}

// Limpa espaços em branco
$('#formulario input[type=text], #formulario input#email').on('blur', function(e) {
    $(this).val($.trim($(this).val()))
    console.log($.trim($(this).val()))
})

// Escuta evento que é invocado quando o modal acaba de ser oculto ao usuário
$('#modalFormulario').on('show.bs.modal', function (event) {
    var botao = $(event.relatedTarget)  // Botão que acionou o modal
    var acao = botao.data('action')  // Extrai informação do atributo data-action
    var modal = $(this);
    var formulario = modal.find('form')

    switch (acao) {
        case 'adicionar':
            modal.find('#rotuloModal').text('Adicionar membro')

            formulario.find('#idRegistro').val(-1)
            formulario.find('#submit').val('Adicionar')
            break

        case 'editar':
            modal.find('#rotuloModal').text('Editar membro')

            var registro = botao.closest('tr')  // Encontra a linha do registro
            var idRegistro = registro.parent().children().index(registro)  // Índice do registro
            formulario.find('#idRegistro').val(idRegistro)
            formulario.find('#submit').val('Salvar')

            var nome = registro.find('td[data-campo=nome]').text()
            var cargo = registro.find('td[data-campo=cargo]').text()
            var email = registro.find('td[data-campo=email]').text()
            // Converte a data de admissao do formato dd/mm/YYYY para YYYY-mm-dd
            var admissao = registro.find('td[data-campo=admissao]').text()
                                .replace(/(\d{2})\/(\d{2})\/(\d{4})/, '$3-$2-$1')

            formulario.find('#nome').val(nome)
            formulario.find('#cargo').val(cargo)
            formulario.find('#email').val(email)
            formulario.find('#admissao').val(admissao)
            break

        default:
            // Se o modal foi aberto por um evento não reconhecido, ele deve ser fechado
            modal.modal('hide')
            formulario.find('#idRegistro').val("")
            break;
    }
})

// Escuta evento que é invocado quando o modal acaba de ser oculto ao usuário
$('#modalFormulario').on('hidden.bs.modal', function (event) {
    $(this).find('#rotuloModal').text('')  // Limpa rótulo do modal
    $(this).find('form')[0].reset()  // Limpa campos do formulário
    $(this).find('form').find("#idRegistro").val(-1)
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

    // Converte a data de admissao do formato YYYY-mm-dd para dd/mm/YYYY
    data.admissao = data.admissao.replace(/(\d{4})-(\d{2})-(\d{2})/, '$3\/$2\/$1')

    var novoRegistro = formataRegistro(data)
    var corpoTabela = $('#tabelaMembros tbody')

    if (data.idRegistro < 0) {  // Adiciona novo registro
        corpoTabela.append(novoRegistro)
        incrementaRegistros()
    }
    else {  // Edita registro
        // Insere novo registro após o registro editado
        $(novoRegistro).insertAfter(corpoTabela.children().get(data.idRegistro))
        // Remove registro antigo
        corpoTabela.children().get(data.idRegistro).remove()
    }

    $("#modalFormulario").modal('hide')
})

// Trata remoção de registro
$(document).on('click', 'input.remover', function(event) {
    $(this).closest('tr').remove()
    decrementaRegistros()
})