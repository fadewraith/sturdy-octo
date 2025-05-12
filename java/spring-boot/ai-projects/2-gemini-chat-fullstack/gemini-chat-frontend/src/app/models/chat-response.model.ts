export interface ChatResponse {
  candidates: Candidate[];
  usageMetadata: UsageMetadata;
}

export interface Candidate {
  content: { parts: { text: string }[] };
  citationMetadata?: { citationSources: CitationSource[] };
}

export interface CitationSource {
  uri: string;
  startIndex: number;
  endIndex: number;
}

export interface UsageMetadata {
  promptTokenCount: number;
  candidatesTokenCount: number;
  totalTokenCount: number;
}
