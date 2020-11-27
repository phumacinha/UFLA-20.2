// URL de requisicoes
var url = "http://localhost:8080/agenda2/contatos"

var camposEndereco = ['#endereco', '#bairro', '#estado', '#cidade']

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
                <td class="align-middle" data-campo="numero">'+contato.numero+'</td>\
                <td class="align-middle" data-campo="bairro">'+contato.bairro+'</td>\
                <td class="align-middle" data-campo="cidade">'+contato.cidade+'</td>\
                <td class="align-middle" data-campo="estado">'+contato.estado+'</td>\
                <td class="align-middle" data-campo="cep">'+contato.cep+'</td>\
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

// Desabilta campos relacionados a endereço, exceto número
function desativaCamposEndereco(preenchimento = null, disabled = true) {
    $.each(camposEndereco, function(id, campo) {
        // Passa valores para o campo, caso existam
        var preenchimentoAux = preenchimento !== null ? preenchimento : $(campo).val()
        
        // Desabilita ou habilita campos
        $(campo).val(preenchimentoAux).prop("disabled", disabled)
    })
}

// Ativa campos relacionados a endereço
function ativaCamposEndereco(preenchimento = null) {
    desativaCamposEndereco(preenchimento, false)
}

// Exibe mensagem caso o CEP seja inválido
function cepInvalido(mensagem) {
    var campoCep = $("#cep")
    var cepFeedback = $(".cep-feedback")

    // Se mensagem for false, o cep é válido.
    if (mensagem === false)
        campoCep.removeClass('is-invalid')
    else {
        cepFeedback.text(mensagem)
        campoCep.addClass('is-invalid')
        campoCep.focus()
    }
}

// Espera documento ficar pronto
$(document).ready(function () {
    // Define máscara adaptiva para telefone
    var mascaraTelefone = function (val) {
        return val.replace(/\D/g, '').length === 11 ? '(00) 00000-0000' : '(00) 0000-00009';
    },
    options = {
        onKeyPress: function (val, e, field, options) {
            field.mask(mascaraTelefone.apply({}, arguments), options);
        }
    };
    // Coloca máscara no campo telefone
    $('#telefone').mask(mascaraTelefone, options);
    
    // Coloca máscara no campo CPF
    $("#cep").mask("00000-000")
    
    //Quando o campo cep é modificado
    $("#cep").change(function() {
        // Limpa formatação do campo CEP
        cepInvalido(false)

        //Nova variável "cep" somente com dígitos.
        var cep = $(this).val().replace(/\D/g, '');

        //Verifica se campo cep possui valor informado.
        if (cep != "") {

            //Expressão regular para validar o CEP.
            var validacep = /^[0-9]{8}$/;

            //Valida o formato do CEP.
            if(validacep.test(cep)) {

                //Preenche os campos com "..." enquanto consulta webservice.
                desativaCamposEndereco("Buscando...")

                //Consulta o webservice viacep.com.br/
                $.getJSON("https://viacep.com.br/ws/"+ cep +"/json/", function(dados) {
                    // CEP invalido
                    if (("erro" in dados)) {
                        ativaCamposEndereco("")
                        cepInvalido("CEP não encontrado.")
                    }
                    
                    else {                    
                        var matches = {
                            'logradouro': '#endereco',
                            'bairro': '#bairro',
                            'uf': '#estado',
                            'localidade': '#cidade'
                        }
                        
                        var proximoCampo = '#numero';
                        
                        //Atualiza os campos com os valores da consulta.    
                        $.each(matches, function(chave, seletor) {
                            if (dados[chave] !== "")
                                $(seletor).val(dados[chave])
                            else {
                                $(seletor).val("")
                                proximoCampo = proximoCampo === '#numero'
                                                ? seletor : proximoCampo
                            }
                        })
                        
                        // Reativa campos
                        ativaCamposEndereco()
                        $(proximoCampo).focus()
                    } //end if.
                });
                
                
            } //end if.
            else {
                //cep é inválido
                cepInvalido("Formato de CEP inválido.")
                // Reativa campos
                ativaCamposEndereco()
            }
        } //end if.
        else {
            //cep sem valor, limpa formulário.
            cepInvalido(false)
        }
    });
    
    // Limpa espaços em branco
    $('#formulario input[type=text], #formulario input#email').on('blur', function(e) {
        $(this).val($.trim($(this).val()))
    })

    // Escuta evento que é invocado quando o modal acaba de ser exibido ao usuário
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
                var numero = registro.find('td[data-campo=numero]').text()
                var bairro = registro.find('td[data-campo=bairro]').text()
                var cidade = registro.find('td[data-campo=cidade]').text()
                var estado = registro.find('td[data-campo=estado]').text()
                var cep = registro.find('td[data-campo=cep]').text()

                formulario.find('#nome').val(nome)
                formulario.find('#telefone').val(telefone)
                formulario.find('#email').val(email)
                formulario.find('#cep').val(cep)
                formulario.find('#endereco').val(endereco)
                formulario.find('#numero').val(numero)
                formulario.find('#bairro').val(bairro)
                formulario.find('#estado').val(estado)
                formulario.find('#cidade').val(cidade)
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
                    registro.find("td[data-campo=numero]").text(contato.numero)
                    registro.find("td[data-campo=bairro]").text(contato.bairro)
                    registro.find("td[data-campo=cidade]").text(contato.cidade)
                    registro.find("td[data-campo=estado]").text(contato.estado)
                    registro.find("td[data-campo=cep]").text(contato.cep)
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

})