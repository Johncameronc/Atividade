# Aplicativo de Usuários e Tarefas - JSONPlaceholder

Aplicativo Android desenvolvido em Kotlin com Jetpack Compose que consome a API JSONPlaceholder para listar usuários e suas respectivas tarefas.

## Integrantes

- **John Claude Cameron Chappell**

## Funcionalidades

### Requisitos Obrigatórios Implementados

✅ **Modelos de Dados**
- `Usuario(id: Int, name: String, username: String, email: String)`
- `Tarefa(id: Int, title: String, completed: Boolean)`

✅ **Integração com API**
- Retrofit + Gson para consumo da API JSONPlaceholder
- Endpoints: `GET /users` e `GET /users/{id}/todos`
- Base URL: `https://jsonplaceholder.typicode.com/`

✅ **Navegação (Navigation Compose)**
- Fluxo: Lista de Usuários → Detalhes do Usuário → Tarefas do Usuário
- Sistema de navegação com argumentos dinâmicos

✅ **Interface de Usuário**
- **Tela Principal**: Lista todos os usuários com username e nome
- **Tela de Detalhes**: Exibe informações completas do usuário selecionado
- **Tela de Tarefas**: Lista todas as tarefas do usuário com status de conclusão

✅ **Gerenciamento de Estado**
- Estados de loading, erro e sucesso
- Uso de `remember`, `mutableStateOf` e `LaunchedEffect`
- Tratamento de exceções com mensagens de erro

## 🔗 Abordagem de Navegação Utilizada

### Estratégia Híbrida: JSON + Parâmetros Separados

**Rota 1: Principal → Detalhes**
```
"detalhes/{usuarioJson}"
```
- Passa o objeto `Usuario` completo serializado em JSON
- **Vantagem**: Não precisa fazer nova requisição para obter dados do usuário
- **Implementação**: `Gson().toJson(usuario)` + `URLDecoder.decode()`

**Rota 2: Detalhes → Tarefas**
```
"tarefas/{userId}/{username}"
```
- Passa apenas `userId` (necessário para API) e `username` (para exibição)
- **Vantagem**: URLs mais limpas e parâmetros tipados
- **Implementação**: Argumentos separados com `NavType.IntType` e `NavType.StringType`

### Estrutura de Navegação
```kotlin
NavHost(startDestination = "principal") {
    composable("principal") { TelaPrincipal() }
    
    composable(
        route = "detalhes/{usuarioJson}",
        arguments = listOf(navArgument("usuarioJson") { type = NavType.StringType })
    ) { ... }
    
    composable(
        route = "tarefas/{userId}/{username}",
        arguments = listOf(
            navArgument("userId") { type = NavType.IntType },
            navArgument("username") { type = NavType.StringType }
        )
    ) { ... }
}
```

## Extras Implementados

### 1. **Interface Visual Aprimorada**
**Como testar:**
- Execute o app e observe o design moderno com Material Design 3
- Cards com elevação e espaçamento consistente
- Ícones visuais para status das tarefas (✓ verde / ✗ vermelho)

### 2. **Logging de Requisições HTTP**
**Como testar:**
- Execute o app e abra o Logcat no Android Studio
- Filtre por "OkHttp" para ver todas as requisições e respostas da API
- Útil para debug e desenvolvimento

### 3. **Indicadores Visuais de Status**
**Como testar:**
- Navegue até a tela de tarefas de qualquer usuário
- Observe os ícones coloridos indicando se a tarefa está concluída:
  - ✅ **Verde**: Tarefa concluída (`completed: true`)
  - ❌ **Vermelho**: Tarefa pendente (`completed: false`)

### 4. **Estados de Loading e Erro Robustos**
**Como testar:**
- **Loading**: Execute o app - verá indicadores de carregamento em todas as telas
- **Erro de Rede**: Desative o WiFi/dados móveis e tente acessar uma tela
- **Tratamento de Erro**: Mensagens de erro claras e amigáveis

### 5. **Navegação com Botão Voltar**
**Como testar:**
- Use a seta de voltar nas telas de Detalhes e Tarefas
- Também funciona com o botão físico/gesture de voltar do Android

### 6. **Configuração de Segurança de Rede**
**Como testar:**
- O app funciona corretamente mesmo em dispositivos com Android 9+ (API 28+)
- Requisições HTTP são permitidas através da configuração `usesCleartextTraffic="true"`

## Configuração e Execução

### Pré-requisitos
- Android Studio Arctic Fox ou superior
- Kotlin 1.8+
- Android SDK API 24+ (Android 7.0)

### Dependências Principais
```kotlin
// Navegação
implementation("androidx.navigation:navigation-compose:2.7.5")

// Rede
implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")
implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

// Corrotinas
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
```

### Como Executar
1. Clone/baixe o projeto
2. Abra no Android Studio
3. Sync do Gradle
4. Execute no dispositivo/emulador
5. Certifique-se de ter conexão com internet

## Fluxo de Uso

1. **Tela Inicial**: Lista de usuários carrega automaticamente
2. **Clique em um usuário**: Navega para detalhes completos
3. **Botão "Abrir Tarefas"**: Mostra todas as tarefas do usuário
4. **Navegação**: Use as setas de voltar ou gestures do Android

## 🔧 Arquitetura

- **UI**: Jetpack Compose
- **Navegação**: Navigation Compose
- **Rede**: Retrofit + OkHttp + Gson
- **Concorrência**: Kotlin Coroutines
- **Estado**: Compose State Management
- **Arquitetura**: MVVM simplificado (sem ViewModels formais)

## API Utilizada

**JSONPlaceholder**: Fake REST API para testes
- Base URL: `https://jsonplaceholder.typicode.com/`
- Endpoints:
  - `GET /users` - Lista todos os usuários
  - `GET /users/{id}/todos` - Lista tarefas de um usuário específico