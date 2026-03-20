import { useState } from 'react';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import api from '../axios';

export default function Dashboard() {
  const [url, setUrl] = useState('');
  const queryClient = useQueryClient();

  const { data: urls = [] } = useQuery({
    queryKey: ['urls'],
    queryFn: () => api.get('/api/urls').then(r => r.data)
  });

  const shorten = useMutation({
    mutationFn: (originalUrl: string) => api.post('/api/urls/shorten', { originalUrl }),
    onSuccess: () => { queryClient.invalidateQueries({ queryKey: ['urls'] }); setUrl(''); }
  });

  return (
    <div className="min-h-screen bg-gray-50 p-8">
      <div className="max-w-2xl mx-auto">
        <h1 className="text-2xl font-semibold mb-6">My Links</h1>
        <div className="flex gap-2 mb-8">
          <input className="flex-1 border rounded-lg px-4 py-2" type="url"
            placeholder="https://example.com" value={url}
            onChange={e => setUrl(e.target.value)} />
          <button
            className="bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700"
            onClick={() => url && shorten.mutate(url)}>
            Shorten
          </button>
        </div>
        <div className="space-y-3">
          {urls?.map((u: any) => (
            <div key={u.id} className="bg-white rounded-lg p-4 border flex justify-between items-center">
              <div>
                <p className="font-medium text-sm text-blue-600">
                  http://localhost:8080/{u.shortCode}
                </p>
                <p className="text-gray-500 text-xs mt-1 truncate max-w-sm">{u.originalUrl}</p>
              </div>
              <span className="text-gray-400 text-sm">{u.clickCount} clicks</span>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}