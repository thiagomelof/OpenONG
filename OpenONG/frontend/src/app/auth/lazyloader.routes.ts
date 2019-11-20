import { RouterModule, Routes } from '@angular/router';
import { AuthComponent } from './auth.component';
import { DashboardCrmComponent } from '../dashboard-crm/dashboard-crm.component';

export const appRoutes: Routes = [{
    path: '', component: AuthComponent, children: [
        { path: 'dashboard', component: DashboardCrmComponent },
        { path: 'material-widgets', loadChildren: '../material-widgets/material-widgets.module#MaterialWidgetsModule' },
        { path: 'tables', loadChildren: '../tables/tables.module#TablesModule' },
        { path: 'maps', loadChildren: '../maps/maps.module#MapsModule' },
        { path: 'charts', loadChildren: '../charts/charts.module#ChartsModule' },
        // { path: 'chats', loadChildren: '../chats/chat.module#ChatsModule' }, // fix this
        //{ path: 'mail', loadChildren: '../mail/mail.module#MailModule' }, // fix this
        { path: 'pages', loadChildren: '../pages/pages.module#PagesModule' },
        { path: 'forms', loadChildren: '../forms/forms.module#FormModule' }, //fix this
        { path: 'guarded-routes', loadChildren: '../guarded-routes/guarded-routes.module#GuardedRoutesModule' },
        // { path: 'editor', loadChildren: '../editor/editor.module#EditorModule' }, 
        { path: 'scrumboard', loadChildren: '../scrumboard/scrumboard.module#ScrumboardModule' },
        { path: 'categoria', loadChildren: '../categoria/categoria.module#CategoriaModule' },
        { path: 'categoria/:id', loadChildren: '../categoria/categoria.module#CategoriaModule' },
        { path: 'item', loadChildren: '../item-servico/item-servico.module#ItemServicoModule' },
        { path: 'item/:id', loadChildren: '../item-servico/item-servico.module#ItemServicoModule' },
        { path: 'parceiro', loadChildren: '../parceiro-de-negocio/parceiro-de-negocio.module#ParceiroDeNegocioModule' },
        { path: 'parceiro/:id', loadChildren: '../parceiro-de-negocio/parceiro-de-negocio.module#ParceiroDeNegocioModule' },
        { path: 'convenio', loadChildren: '../convenio/convenio.module#ConvenioModule' },
        { path: 'convenio/:id', loadChildren: '../convenio/convenio.module#ConvenioModule' },
        { path: 'despesa', loadChildren: '../despesa/despesa.module#DespesaModule' },
        { path: 'despesa/:id', loadChildren: '../despesa/despesa.module#DespesaModule' },
        { path: 'doacao', loadChildren: '../doacao/doacao.module#DoacaoModule' },
        { path: 'doacao/:id', loadChildren: '../doacao/doacao.module#DoacaoModule' },
        { path: 'beneficiado', loadChildren: '../beneficiado/beneficiado.module#BeneficiadoModule' },
        { path: 'beneficiado/:id', loadChildren: '../beneficiado/beneficiado.module#BeneficiadoModule' },
        { path: 'usuario', loadChildren: '../usuario/usuario.module#UsuarioModule' },
        { path: 'usuario/:id', loadChildren: '../usuario/usuario.module#UsuarioModule' },
        { path: 'relatorio', loadChildren: '../relatorio/relatorio.module#RelatorioModule' }
    ]
}];
