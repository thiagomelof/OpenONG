export const menus = [
    {
        'name': 'Gráficos',
        'link': '/auth/dashboard',
        'icon': 'assessment',
        'open': false,
    },
    {
        'name': 'Cadastros',
        'icon': 'dashboard',
        'link': false,
        'open': false,
        'sub': [
            {
                'name': 'Doadores e Fornecedores',
                'link': '/auth/parceiro',
                'icon': 'account_box',
                'chip': false,
                'open': true,
            },
            {
                'name': 'Beneficiados',
                'link': '/auth/beneficiado',
                'icon': 'supervisor_account',
                'chip': false,
                'open': true,
            },
            {
                'name': 'Itens e serviços',
                'link': '/auth/item',
                'icon': 'list',
                'chip': false,
                'open': true,
            },
            {
                'name': 'Categoria de item',
                'link': '/auth/categoria',
                'icon': 'list',
                'chip': false,
                'open': true,
            }
        ]
    },
    {
        'name': 'Convênios',
        'link': '/auth/convenio',
        'icon': 'assignment',
        'open': false,
    },
    {
        'name': 'Doações',
        'icon': 'favorite',
        'link': '/auth/doacao',
        'open': false,
    },
    {
        'name': 'Despesas',
        'icon': 'local_grocery_store',
        'link': '/auth/despesa',
        'open': false,
    },
         {
        'name': 'Relatórios',
        'icon': 'content_copy',
        'open': false,
        'link': false,
        'sub': [
            {
                'name': 'Despesas',
                'icon': 'description',
                'open': false,
                'link': '/auth/relatorio/reldespesa',
            }, {
                'name': 'Doações',
                'icon': 'description',
                'open': false,
                'link': '/auth/relatorio/reldoacao',
            }, {
                'name': 'Consumo por convênio',
                'icon': 'description',
                'open': false,
                'link': '/auth/relatorio/relconsumo',
            }
        ]
    }
];
