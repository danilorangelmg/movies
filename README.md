# ARQUITETURA DO PROJETO

### Desenvolvimento em Geral

- É utilizado o padrão de desenvolvimento Model View Presenter(MVP).
- Todas as telas são preferenciamente Activitys, utilizar Fragments apenas em casos de real necessidade. 
- Toda a aplicação deve ser desenvolvida em Inglês, com resalva apenas para comentários a nivel de classe, método ou local.
- Todo Activity ou Fragment deve conter seu presenter e a interface de contrato que fará a ligação entre a view e o presenter.
- Toda a chamada de serviço(Api) deverá ser feita por seu respectivo Repository, implementando seu Callback respeitando a arquitetura do projeto. 
- Não deixar qualquer tipo de dado hardcoded.
- Sempre implementar os metodos savedInstance e restoreInstance 
da Activity/Fragment(apenas savedInstance) afim de garantir que a aplicação não perca nenhuma informação. 

### Gerenciamento de branchs (GitFlow)

- No inicio da Sprint serão apagadas todas as branchs da Sprint anterior exceto master e develop.
- Para criação de branchs se padronizou o uso de "-" para separar as palavras ou contextos 
não poderá ser utilizado "_" ou outros caracteres especiais.
- Todos os merges deverão ser feitos via PullRequest na branch originária(ou criada a partir) 
com review e aprovação de outro membro do time.
- Sempre que criada a branch release/v-versionName todos os bugfix referente a 
tasks já mergeadas nessa branch ou que necessitem de ser corrigidos na mesma versão deverão ser criadas a partir da branch release e não da develop.
- Nas atividades normais da Sprint antes da criação da branch de release/v-versionName o bugfix deverá ser criado a partir da develop.
- Em caso de correções de produção será criada a partir da master a branch hotfixes/v-versionName (Ex. hotfixes/v-2.1.6) 
  sendo o versionName o número da versão a ser disponibilizada.
- Para cada hotfix de produção será criada uma branch(hotfix/taskNumber-description) a partir da branch hotfixes e mergeada na mesma posteriormente.
- Após atualizar uma versão de hotfixes durante a Sprint, a mesma deverá ser mergeada na master e posteriormente na develop.
- Ao fim da Sprint fazer o merge da branch release na master e posteriormente na develop.
- Sempre gerar a Tag referente a versão disponibilizada em produção. Ex. v-2.1.6

##### Nomenclatura de branchs

| Nomenclatura                    | Exemplo                | Criada a partir                  | Observação                                                                         |
| :---------------------------:   |:----------------------:|:--------------------------------:|:---------------------------------------------------------------------------------: |
| hotfixes/v-versionName          |hotfixes/v-2.1.6        | master                           | Agrupa todas as branchs referente a erros de produção                              |
| hotfix/taskNumber-description   |hotfix/1234-descricao   | hotfixes/v-versionName           | Correções de erros de produção                                                     |
| feature/taskNumber-description  |feature/1234-descricao  | develop                          | Atividades da Sprint ativa                                                         |
| bugfix/taskNumber-description   |bugfix/1234-descricao   | develop ou release/v-versionName | Correções da Sprint ativa, se cria a partir branch release quando a branch existir |
| release/v-versionName           |release/v-2.1.6         | develop                          | Criada após o termino das atividades da Sprint                                     |
                   
                                     
 ##### Nomenclatura de tags
 
 | Nomenclatura                    | Exemplo                | Criada a partir(Branch)          | Observação                                                                         |
 | :---------------------------:   |:----------------------:|:--------------------------------:|:------------: |
 | v-versionName                   |v-2.1.6                 | master                           |      -        |
 
                                                                          
##### Referências                                    
1. versionName e versionCode:
   São variáveis que definem o nome ou número da versão (2.1.6, abc, etc..) e o 
   código de controle de versão do google, elas se encontram no build.gradle do projeto Android
   ```
   android {
       compileSdkVersion 27
       buildToolsVersion '27.0.0'
       defaultConfig {
           applicationId "package"
           minSdkVersion 21
           targetSdkVersion 27
           versionCode 35
           versionName "2.1.5"
           testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
           multiDexEnabled true
       }
    }
   ```
   O versionName e o versionCode deverão ser alterados(incrementado no caso do versionCode) 
   sempre que se criar uma branch hotfixes ou release.
   
2. taskNumber:
   São as referências das atividades criadas no Jira, Trelo ou qualquer outra plataforma 
   utilizada para gestão do projeto. Ex OLE-1234 (JIRA).
 
3.  description:
    Se trata de uma descrição abreviada e clara da atividade referenciada pela branch. Ex. invoice-detail

### Nomeclatura para Arquivo - strings.xml

    - "action_" -> para ações de click
    - "label_" -> para labels não clicaveis
    - "prompt_" -> para mensagens que são apresentadas para o usuário
    - "error_" -> para tratativas de erro
    - "title_" -> para título da tela
    - "app_" -> para itens relacionados no aplicativo no contexto geral
    

### Nomeclatura para Arquivos XML de Fragment e Activities

    - "nome_do_fragment_" ou "nome_da_activity_"

    - "toolbar" -> Toolbar
    - "tv_" -> TextView
    - "bt_" -> Button
    - "sp_" -> Spinner
    - "et_" -> EditText
    - "rb_" -> RadioButton
    - "cb_" -> CheckBox
    - "rl_" -> RelativeLayout
    - "ll_" -> LinearLayout
    - "rv_" -> RecyclerView
    - "iv_" -> ImageView
    - "tl_" -> TextInputLayout
    - "cl_" -> ConstraintLayout
    - "vw_" -> View
    - "mv_" -> MultiViewPager
    - "fg_" -> Fragment
    - "cv_" -> CardView
    - "sv_" -> ScrollView / NestedScrollView
    - "sf_" -> SurfaceView

### Nomeclatura para arquivos Fragments, Activities, etc...

O nome deve seguir o padrão de Fragment(NomeDoQueEleRepresenta), Activity(NomeDoQueEleRepresenta)...
Ex: FragmentLogin (Representa a parte de login do sistema).

### Links uteis

1. GitFlow
    
    http://nvie.com/posts/a-successful-git-branching-model/

2. Documentação Android
    
    https://developer.android.com
    
3. MVP
   
   https://codelabs.developers.google.com/codelabs/android-testing/index.html#0
        
4. Outros
    
    https://adityaladwa.wordpress.com/2016/05/11/dagger-2-and-mvp-architecture/
    
    https://android.jlelse.eu/mvp-dagger-2-rx-clean-modern-android-app-code-74f63c9a6f2f
    
