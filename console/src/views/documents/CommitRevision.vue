<script setup lang="ts">
import { onMounted, ref } from "vue";
import { http } from "@/utils/http";

type Row = Record<string, any>;
const documents = ref<Row[]>([]);
const selectedId = ref("");
const content = ref("");
const schema = ref("v1");
const version = ref(0);
const loading = ref(false);
const committing = ref(false);
const error = ref("");
const message = ref("");

async function loadDocuments() {
  loading.value = true; error.value = "";
  try {
    const result = await http.get<unknown, unknown>("/documents");
    documents.value = Array.isArray(result) ? result as Row[] : [];
    if (!selectedId.value && documents.value[0]?.id) {
      selectedId.value = String(documents.value[0].id);
      await loadCopy();
    }
  } catch (e: any) { error.value = e?.response?.data?.detail || e?.message || "文档列表加载失败"; }
  finally { loading.value = false; }
}

async function loadCopy() {
  if (!selectedId.value) return;
  loading.value = true; error.value = ""; message.value = "";
  try {
    const copy: any = await http.get(`/documents/${selectedId.value}/working-copy`);
    content.value = String(copy?.content || "");
    schema.value = String(copy?.contentSchemaVersion || "v1");
    version.value = Number(copy?.version || 0);
  } catch (e: any) { error.value = e?.response?.data?.detail || e?.message || "工作副本加载失败"; }
  finally { loading.value = false; }
}

async function commitRevision() {
  if (!selectedId.value || !content.value.trim()) { error.value = "请选择文档并确认内容不为空"; return; }
  committing.value = true; error.value = ""; message.value = "";
  try {
    const result: any = await http.post(`/documents/${selectedId.value}/revisions`, {
      data: { content: content.value, contentSchemaVersion: schema.value, expectedVersion: version.value }
    });
    message.value = `已创建 Revision v${result?.revisionNumber ?? ""}`;
    await loadCopy();
  } catch (e: any) {
    error.value = e?.response?.status === 409 ? "工作副本版本已变化，请重新加载后再提交" : e?.response?.data?.detail || e?.message || "提交 Revision 失败";
  } finally { committing.value = false; }
}

onMounted(loadDocuments);
</script>

<template>
  <main class="p-4 md:p-6">
    <div class="flex flex-wrap items-start justify-between gap-4 mb-6"><div><h1 class="text-2xl font-semibold">提交协作版本</h1><p class="mt-1 text-[var(--el-text-color-secondary)]">将当前 Working Copy 显式保存为不可变 Revision；不会把工作副本和发布状态混为一谈。</p></div><el-button :loading="loading" @click="loadDocuments">刷新</el-button></div>
    <el-alert v-if="error" :title="error" type="error" show-icon :closable="false" class="mb-4" />
    <el-alert v-if="message" :title="message" type="success" show-icon :closable="false" class="mb-4" />
    <el-card shadow="never"><el-form label-position="top"><el-form-item label="文档" required><el-select v-model="selectedId" filterable class="w-full md:w-96" @change="loadCopy"><el-option v-for="item in documents" :key="item.id" :label="item.title || item.id" :value="item.id" /></el-select></el-form-item><div class="flex flex-wrap items-center gap-3 mb-3"><span class="text-sm">Working Copy 版本：{{ version }}</span><span class="text-sm text-[var(--el-text-color-secondary)]">Schema：{{ schema }}</span><el-button size="small" :loading="loading" :disabled="!selectedId" @click="loadCopy">重新加载</el-button></div><el-form-item label="提交内容" required><el-input v-model="content" type="textarea" :rows="18" :disabled="!selectedId || loading" /></el-form-item><el-button type="primary" :loading="committing" :disabled="!selectedId" @click="commitRevision">提交为新 Revision</el-button></el-form></el-card>
  </main>
</template>
