## Simulador de Memoria e Escalonador

O escalonamento FCFS (First-Come, First Served) / FIFO (First-In-First-Out), é uma abordagem básica para a ordem de execução de processos, onde as tarefas são tratadas na ordem em que chegam à fila de tarefas prontas.

Neste projeto, a fila de processos prontos e a fila de processos bloqueados são unificadas em uma única fila de processos bloqueados. O algoritmo apresentado representa o modo cooperativo de escalonamento FCFS, no qual o processador executa uma tarefa por vez.

Para otimizar o desempenho, os processos são alocados na memória com base em sua posição na fila de bloqueados. O processo em execução e os primeiros processos da fila de bloqueados são armazenados na memória principal. Se a memória principal estiver cheia, os últimos processos da fila de bloqueados são armazenados na memória secundária. Para realizar essa realocação de memória (swap), é necessário que haja espaço igual ou superior ao tamanho do maior processo na memória secundária.
