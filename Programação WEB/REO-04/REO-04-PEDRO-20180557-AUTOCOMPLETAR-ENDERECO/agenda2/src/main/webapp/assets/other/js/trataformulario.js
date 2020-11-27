function desativaCamposEndereco(preenchimento=null, disabled=true) {
    // Limpa valores do formulário de cep.
    if (preenchimento !== null) {
        $("#endereco").val(preenchimento)
        $("#bairro").val(preenchimento)
        $("#estado").val(preenchimento)
        $("#cidade").val(preenchimento)
    }
    
    $("#endereco").prop("disabled", disabled)
    $("#bairro").prop("disabled", disabled)
    $("#estado").prop("disabled", disabled)
    $("#cidade").prop("disabled", disabled)
}

function ativaCamposEndereco() {
    desativaCamposEndereco("", false)
}

function cepInvalido (mensagem) {
    var campoCep = $("#cep")
    var cepFeedback = $(".cep-feedback")
    
    if (mensagem === false) campoCep.removeClass('is-invalid')
    else {
        cepFeedback.text(mensagem)
        campoCep.addClass('is-invalid')
    }
}

$(document).ready(function() {
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
    
    
    //Quando o campo cep perde o foco.
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
                $.getJSON("https://viacep.com.br/ws/"+ cep +"/json/?callback=?", function(dados) {

                    if (!("erro" in dados)) {
                        
                        //Atualiza os campos com os valores da consulta.                        
                        var matches = {
                            'logradouro': '#endereco',
                            'bairro': '#bairro',
                            'uf': '#estado',
                            'localidade': '#cidade'
                        }
                        
                        var proximoCampo = '#numero';
                        
                        $.each(matches, function(chave, seletor) {
                            if (dados[chave] != "")
                                $(seletor).val(dados[chave]).prop("disabled", true)
                            else {
                                $(seletor).val("").prop("disabled", false)
                                proximoCampo = proximoCampo === '#numero'
                                                ? seletor : proximoCampo
                            }
                        })
                        
                        $(proximoCampo).focus()
                    } //end if.
                    else {
                        //CEP pesquisado não foi encontrado.
                        ativaCamposEndereco(false)
                        cepInvalido("CEP não encontrado.")
                    }
                });
            } //end if.
            else {
                //cep é inválido.
                ativaCamposEndereco(false)
                cepInvalido("Formato de CEP inválido.")
            }
        } //end if.
        else {
            //cep sem valor, limpa formulário.
            ativaCamposEndereco(false)
            cepInvalido(false)
        }
    });
})