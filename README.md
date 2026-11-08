# MatemáticaApp

App Android que ensina matemática com exercícios interativos.

## Como compilar

### Opção 1: Local (precisa do JDK 17)

```bash
gradle wrapper --gradle-version 8.5
./gradlew assembleDebug
```

O APK ficará em `app/build/outputs/apk/debug/app-debug.apk`.

### Opção 2: GitHub Actions (sem instalar nada)

1. Suba este repositório para o GitHub.
2. Vá na aba **Actions**.
3. Aguarde o workflow terminar.
4. Baixe o artefato **app-debug** e extraia o APK.
